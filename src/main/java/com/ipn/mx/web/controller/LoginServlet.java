/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.web.controller;

import com.ipn.mx.DAO.UsuarioDAO;
import com.ipn.mx.DTO.UsuarioDTO;
import com.ipn.mx.utilerias.LoginManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author unityofdisaster
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private LoginManager lm = new LoginManager();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion.equals("iniciarSesion")) {
            iniciarSesion(request, response);
        } else if (accion.equals("cerrarSesion")) {
            cerrarSesion(request, response);
        }
    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        String usuario = request.getParameter("txtUsuario");
        String pass = request.getParameter("txtPass");
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        dto.getEntidad().setNombreUsuario(usuario);
        try {
            dto = dao.buscar(dto);
            if (dto == null) {
                request.setAttribute("mensajeError", true);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else if (pass.equals(dto.getEntidad().getClaveUsuario())) {
                lm.login(request, response, usuario);

                // evitar que se presione el boton hacia atras
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("max-age", 0);
                response.setDateHeader("Expires", 0);
                // redireccionamiento a menu principal
                response.sendRedirect("menuPrincipal.jsp");
            } else {
                request.setAttribute("mensajeError", true);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                // response.sendRedirect("index.jsp");
            }
        } catch (SQLException | IOException | ServletException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) {
        lm.logout(request, response);
        try {
            response.sendRedirect(request.getServletContext().getContextPath());
        } catch (IOException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
