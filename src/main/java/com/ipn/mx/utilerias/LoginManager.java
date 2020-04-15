/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.utilerias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author unityofdisaster
 */
public class LoginManager {
    private final static String LOGIN_NAME_SESSION_ATTRIBUTE = "nombreUsuario";
    
    public void login(HttpServletRequest request, HttpServletResponse response, String nombreUsuario){
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_NAME_SESSION_ATTRIBUTE, nombreUsuario);
    }
    
    public void logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        session.removeAttribute("nombreUsuario");
        if(session != null){
            session.invalidate();
        }
    }
    
    public boolean getLoginName(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if(session == null){
            return false;
        }else{
            return session.getAttribute(LOGIN_NAME_SESSION_ATTRIBUTE) != null;
        }
        
    }
}
