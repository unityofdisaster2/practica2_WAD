/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.web.controller;

import com.ipn.mx.DAO.AlumnoDAO;
import com.ipn.mx.DAO.CarreraDAO;
import com.ipn.mx.DTO.AlumnoDTO;
import com.ipn.mx.DTO.CarreraDTO;
import com.ipn.mx.utilerias.Utilerias;
import java.io.ByteArrayInputStream;
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
@WebServlet(name = "AlumnoServlet", urlPatterns = {"/AlumnoServlet"})
@MultipartConfig(maxFileSize = 16000000)
public class AlumnoServlet extends HttpServlet {

    private Utilerias correos = new Utilerias();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion.equals("listaDeAlumnos")) {
            listaDeAlumnos(request, response);
        } else if (accion.equals("nuevo")) {
            agregarAlumno(request, response);
        } else if (accion.equals("eliminar")) {
            eliminarAlumno(request, response);
        } else if (accion.equals("actualizar")) {
            actualizarAlumno(request, response);
        } else if (accion.equals("guardar")) {
            try {
                almacenarAlumno(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (accion.equals("generarReporte")) {
            generarReporteAlumno(request, response);
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

    private void listaDeAlumnos(HttpServletRequest request, HttpServletResponse response) {
        AlumnoDAO dao = new AlumnoDAO();
        List lista = new ArrayList();
        try {
            lista = dao.obtenerTodos();

            CarreraDAO daoC = new CarreraDAO();
            List listaCarrera = daoC.obtenerTodos();
            HashMap<Integer, String> mapaCarrera = new HashMap();
            for (int i = 0; i < listaCarrera.size(); i++) {
                mapaCarrera.put(((CarreraDTO) listaCarrera.get(i)).getEntidad().getIdCarrera(), ((CarreraDTO) listaCarrera.get(i)).getEntidad().getNombreCarrera());
            }
            request.setAttribute("mapaCarrera", mapaCarrera);

            request.setAttribute("listaAlumnos", lista);
            RequestDispatcher vista = request.getRequestDispatcher("/alumno/listaAlumnos.jsp");
            vista.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void agregarAlumno(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher vista = request.getRequestDispatcher("/alumno/alumnoForm.jsp");
        try {
            CarreraDAO dao = new CarreraDAO();
            List listaCarrera = dao.obtenerTodos();

            request.setAttribute("listaCarrera", listaCarrera);
            vista.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarAlumno(HttpServletRequest request, HttpServletResponse response) {
        AlumnoDAO dao = new AlumnoDAO();
        AlumnoDTO dto = new AlumnoDTO();
        dto.getEntidad().setIdAlumno(Integer.parseInt(request.getParameter("id")));
        try {
            dao.eliminar(dto);
            request.setAttribute("mensaje", "eliminado");
            listaDeAlumnos(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarAlumno(HttpServletRequest request, HttpServletResponse response) {
        AlumnoDTO dto = new AlumnoDTO();
        AlumnoDAO dao = new AlumnoDAO();
        dto.getEntidad().setIdAlumno(Integer.parseInt(request.getParameter("id")));
        try {
            dto = dao.obtenerUno(dto);
            request.setAttribute("alumno", dto);

            CarreraDAO daoC = new CarreraDAO();
            List listaCarrera = daoC.obtenerTodos();

            request.setAttribute("listaCarrera", listaCarrera);

            request.getRequestDispatcher("/alumno/alumnoForm.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void almacenarAlumno(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        AlumnoDTO dto = new AlumnoDTO();
        AlumnoDAO dao = new AlumnoDAO();
        String id = request.getParameter("id");
        if (!id.equals("")) {
            dto.getEntidad().setIdAlumno(Integer.parseInt(id));
            dto = dao.obtenerUno(dto);

            dto.getEntidad().setNombreAlumno(request.getParameter("txtNombre"));
            dto.getEntidad().setPaternoAlumno(request.getParameter("txtPaterno"));
            dto.getEntidad().setMaternoAlumno(request.getParameter("txtMaterno"));
            dto.getEntidad().setEmailAlumno(request.getParameter("txtEmail"));
            dto.getEntidad().setIdCarrera(Integer.parseInt(request.getParameter("txtCarrera")));

            try {
                Part archivo = null;
                archivo = request.getPart("txtFoto");
                if (!archivo.getInputStream().getClass().getName().equals("java.io.ByteArrayInputStream")) {
                    dto.getEntidad().setImagen(archivo.getInputStream());

                }
                dao.actualizar(dto);
                request.setAttribute("mensaje", "actualizado");
                listaDeAlumnos(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServletException ex) {
                Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            dto.getEntidad().setNombreAlumno(request.getParameter("txtNombre"));
            dto.getEntidad().setPaternoAlumno(request.getParameter("txtPaterno"));
            dto.getEntidad().setMaternoAlumno(request.getParameter("txtMaterno"));
            dto.getEntidad().setEmailAlumno(request.getParameter("txtEmail"));
            dto.getEntidad().setIdCarrera(Integer.parseInt(request.getParameter("txtCarrera")));

            try {
                Part archivo = null;
                archivo = request.getPart("txtFoto");
                if (archivo != null) {
                    System.out.println(archivo.getInputStream());
                    dto.getEntidad().setImagen(archivo.getInputStream());
                } else {
                    System.out.println("sin imagen");
                }

                dao.crear(dto);
                CarreraDTO dtoCar = new CarreraDTO();
                CarreraDAO daoCar = new CarreraDAO();
                dtoCar.getEntidad().setIdCarrera(dto.getEntidad().getIdCarrera());
                dtoCar = daoCar.obtenerUno(dtoCar);
                correos.enviarEmail(dto.getEntidad().getEmailAlumno(), "Alta", "Has sido dado de alta en la carrera: " + dtoCar.getEntidad().getNombreCarrera());
                request.setAttribute("mensaje", "creado");
                listaDeAlumnos(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServletException ex) {
                Logger.getLogger(AlumnoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void generarReporteAlumno(HttpServletRequest request, HttpServletResponse response) {
        AlumnoDAO dao = new AlumnoDAO();
        try {
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/reporteAlumno.jasper"));
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
