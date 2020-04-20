/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.web.controller;

import com.ipn.mx.DAO.CarreraDAO;
import com.ipn.mx.DTO.CarreraDTO;
import com.ipn.mx.utilerias.Grafica;
import com.ipn.mx.utilerias.LoginManager;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author unityofdisaster
 */
@WebServlet(name = "CarreraServlet", urlPatterns = {"/CarreraServlet"})
public class CarreraServlet extends HttpServlet {

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
        String accion = request.getParameter("accion");
        if (accion.equals("listaDeCarreras")) {
            listaDeCarreras(request, response);
        } else if (accion.equals("nuevo")) {
            agregarCarrera(request, response);
        } else if (accion.equals("eliminar")) {
            eliminarCarrera(request, response);
        } else if (accion.equals("actualizar")) {
            actualizarCarrera(request, response);
        } else if (accion.equals("guardar")) {
            almacenarCarrera(request, response);
        }else if(accion.equals("generarReporte")){
            generarReporteCarrera(request,response);
        }else if(accion.equals("reporteGeneral")){
            generarReporteGeneral(request,response);
        }
        else if(accion.equals("verGrafica")){
            generarGraficaCarrera(request,response);
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

    private void listaDeCarreras(HttpServletRequest request, HttpServletResponse response) {
        CarreraDAO dao = new CarreraDAO();
        List lista = new ArrayList();
        try {
            lista = dao.obtenerTodos();
            request.setAttribute("listaCarreras", lista);
            RequestDispatcher vista = request.getRequestDispatcher("/carrera/listaCarreras.jsp");
            vista.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agregarCarrera(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher vista = request.getRequestDispatcher("/carrera/carreraForm.jsp");
        try {
            vista.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarCarrera(HttpServletRequest request, HttpServletResponse response) {
        CarreraDAO dao = new CarreraDAO();
        CarreraDTO dto = new CarreraDTO();
        dto.getEntidad().setIdCarrera(Integer.parseInt(request.getParameter("id")));
        try {
            dao.eliminar(dto);
            request.setAttribute("mensaje", "eliminado");
            listaDeCarreras(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void actualizarCarrera(HttpServletRequest request, HttpServletResponse response) {
        CarreraDTO dto = new CarreraDTO();
        CarreraDAO dao = new CarreraDAO();
        dto.getEntidad().setIdCarrera(Integer.parseInt(request.getParameter("id")));
        try {
            dto = dao.obtenerUno(dto);
            request.setAttribute("usuario", dto);
            
            request.getRequestDispatcher("/carrera/carreraForm.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void almacenarCarrera(HttpServletRequest request, HttpServletResponse response) {
        CarreraDTO dto = new CarreraDTO();
        CarreraDAO dao = new CarreraDAO();       
        String id = request.getParameter("id");
        if(!id.equals("")){
            dto.getEntidad().setIdCarrera(Integer.parseInt(id));
            dto.getEntidad().setNombreCarrera(request.getParameter("txtNombreCarrera"));
            dto.getEntidad().setDuracion(Integer.parseInt(request.getParameter("txtDuracion")));
            dto.getEntidad().setDescripcion(request.getParameter("txtDescripcion"));
            try{
                dao.actualizar(dto);
                request.setAttribute("mensaje", "actualizado");
                listaDeCarreras(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            dto.getEntidad().setNombreCarrera(request.getParameter("txtNombreCarrera"));
            dto.getEntidad().setDuracion(Integer.parseInt(request.getParameter("txtDuracion")));
            dto.getEntidad().setDescripcion(request.getParameter("txtDescripcion"));
            try{
                dao.crear(dto);
                request.setAttribute("mensaje", "creado");
                listaDeCarreras(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CarreraServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    
    private void generarGraficaCarrera(HttpServletRequest request, HttpServletResponse response) {
        JFreeChart chart = ChartFactory.createPieChart3D("Alumnos por Carrera", getGraficaAlumnos(), true, true,
                            Locale.getDefault());
        String archivo = getServletConfig().getServletContext().getRealPath("/grafica.png");
        try{
            ChartUtils.saveChartAsPNG(new File(archivo), chart, 700, 400);
            RequestDispatcher vista = request.getRequestDispatcher("/carrera/grafica.jsp");
            vista.forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private PieDataset getGraficaAlumnos() {
        DefaultPieDataset pie = new DefaultPieDataset();
        CarreraDAO dao = new CarreraDAO();
        try{
            List datos = dao.graficar();
            for (int i = 0; i < datos.size(); i++) {
                Grafica dto = (Grafica) datos.get(i);
                pie.setValue(dto.getNombre(), dto.getCantidad());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return pie;
    }
    
    
    
    


    private void generarReporteCarrera(HttpServletRequest request, HttpServletResponse response) {
        CarreraDAO dao = new CarreraDAO();
        try {
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/reporteCarrera.jasper"));
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

    private void generarReporteGeneral(HttpServletRequest request, HttpServletResponse response) {
        CarreraDAO dao = new CarreraDAO();
        try{
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/reporteGeneral.jasper"));

            byte [] bytes = JasperRunManager.runReportToPdf(reporte.getPath(), null,dao.conecta());
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            sos.write(bytes, 0, bytes.length);
            sos.flush();
            sos.close();
            
        }catch(IOException | JRException e){
            e.printStackTrace();
        }
        
    }


}
