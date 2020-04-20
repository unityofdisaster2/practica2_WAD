/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.web.controller;

import com.ipn.mx.DAO.UsuarioDAO;
import com.ipn.mx.DTO.UsuarioDTO;
import com.ipn.mx.utilerias.Utilerias;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author unityofdisaster
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
@MultipartConfig(maxFileSize = 16000000)
public class UsuarioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private Utilerias correos = new Utilerias();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion.equals("listaDeUsuarios")) {
            listaDeUsuarios(request, response);
        } else if (accion.equals("nuevo")) {
            agregarUsuario(request, response);
        } else if (accion.equals("eliminar")) {
            eliminarUsuario(request, response);
        } else if (accion.equals("actualizar")) {
            actualizarUsuario(request, response);
        } else if (accion.equals("guardar")) {
            try {
                almacenarUsuario(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(accion.equals("generarReporte")){
            generarReporteUsuario(request,response);
        }        

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listaDeUsuarios(HttpServletRequest request, HttpServletResponse response) {
         UsuarioDAO dao = new UsuarioDAO();
        List lista = new ArrayList();
        try {
            lista = dao.obtenerTodos();

            request.setAttribute("listaUsuarios", lista);
            RequestDispatcher vista = request.getRequestDispatcher("/usuario/listaUsuarios.jsp");
            vista.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        dto.getEntidad().setIdUsuario(Integer.parseInt(request.getParameter("id")));
        try {
            dao.eliminar(dto);
            request.setAttribute("mensaje", "eliminado");
            listaDeUsuarios(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void agregarUsuario(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher vista = request.getRequestDispatcher("/usuario/usuarioForm.jsp");
        try {
            vista.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDTO dto = new UsuarioDTO();
        UsuarioDAO dao = new UsuarioDAO();
        dto.getEntidad().setIdUsuario(Integer.parseInt(request.getParameter("id")));
        try {
            dto = dao.obtenerUno(dto);
            System.out.println(dto.getEntidad().getNombre());
            request.setAttribute("usr", dto);
            request.getRequestDispatcher("/usuario/usuarioForm.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void almacenarUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        UsuarioDTO dto = new UsuarioDTO();
        UsuarioDAO dao = new UsuarioDAO();
        String id = request.getParameter("id");
        System.out.println(request.getParameter("txtNombre"));
        if (!id.equals("")) {
            dto.getEntidad().setIdUsuario(Integer.parseInt(id));
            dto = dao.obtenerUno(dto);

            dto.getEntidad().setNombre(request.getParameter("txtNombre"));
            dto.getEntidad().setPaterno(request.getParameter("txtPaterno"));
            dto.getEntidad().setMaterno(request.getParameter("txtMaterno"));
            dto.getEntidad().setEmail(request.getParameter("txtEmail"));
            dto.getEntidad().setNombreUsuario(request.getParameter("txtNombreUsuario"));
            dto.getEntidad().setClaveUsuario(request.getParameter("txtClaveUsuario"));

            try {
                Part archivo = null;
                archivo = request.getPart("txtFoto");
                if (!archivo.getInputStream().getClass().getName().equals("java.io.ByteArrayInputStream")) {
                    dto.getEntidad().setImagen(archivo.getInputStream());
                }
                dao.actualizar(dto);
                request.setAttribute("mensaje", "actualizado");
                listaDeUsuarios(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServletException ex) {
                Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            dto.getEntidad().setNombre(request.getParameter("txtNombre"));
            dto.getEntidad().setPaterno(request.getParameter("txtPaterno"));
            dto.getEntidad().setMaterno(request.getParameter("txtMaterno"));
            dto.getEntidad().setEmail(request.getParameter("txtEmail"));
            dto.getEntidad().setNombreUsuario(request.getParameter("txtNombreUsuario"));
            dto.getEntidad().setClaveUsuario(request.getParameter("txtClaveUsuario"));

            try {
                Part archivo = null;
                archivo = request.getPart("txtFoto");
                if (archivo != null) {
                    dto.getEntidad().setImagen(archivo.getInputStream());
                } else {
                    System.out.println("sin imagen");
                }

                dao.crear(dto);
                correos.enviarEmail(dto.getEntidad().getEmail(), "Alta", "Se ha registrado usuario exitosamente");
                request.setAttribute("mensaje", "creado");
                listaDeUsuarios(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServletException ex) {
                Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        
    }

    private void generarReporteUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO dao = new UsuarioDAO();
        try {
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/reporteUsuario.jasper"));
            HashMap params = new HashMap();
            params.put("idExterno", request.getParameter("id"));
            byte[] bytes = JasperRunManager.runReportToPdf(reporte.getPath(), params, dao.conecta());
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            sos.write(bytes, 0, bytes.length);
            sos.flush();
            sos.close();

        } catch (IOException | JRException ex) {
            Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
