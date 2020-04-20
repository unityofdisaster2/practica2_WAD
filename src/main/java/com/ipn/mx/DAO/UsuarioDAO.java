/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.DAO;

import com.ipn.mx.DTO.UsuarioDTO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author unityofdisaster
 */
public class UsuarioDAO {

    private static final String SQL_INSERT = "{CALL crear_usuario(?, ?, ?, ?, ?, ?, ?)}";
    private static final String SQL_UPDATE = "{CALL actualizar_usuario(?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String SQL_DELETE = "{CALL borrar_usuario(?)}";
    private static final String SQL_SELECT_ONE = "{CALL obtener_un_usuario(?)}";
    private static final String SQL_SELECT_NOMBRE = "{CALL buscar_usuario(?)}";
    private static final String SQL_SELECT_ALL = "{CALL obtener_usuarios()}";

    
    
    private Connection con;

    
    
    
    
    private void obtenerConexion(){
        try{
            Context ic = new InitialContext();
            //es el nodo en el arbol JNDI donde se pueden encontrar propiedades para el componente JAVA EE actual 
            Context ec = (Context)ic.lookup("java:comp/env");
            DataSource ds = (DataSource)ec.lookup("jdbc/Practica2");
            con = ds.getConnection();
        }catch(NamingException | SQLException ex){
            ex.printStackTrace();
        }
    }


    public Connection conecta(){
        try{
            Context ic = new InitialContext();
            //es el nodo en el arbol JNDI donde se pueden encontrar propiedades para el componente JAVA EE actual 
            Context ec = (Context)ic.lookup("java:comp/env");
            DataSource ds = (DataSource)ec.lookup("jdbc/Practica2");
            con = ds.getConnection();
        }catch(NamingException | SQLException ex){
            ex.printStackTrace();
        }
        return con;
    }
    
    
    public void crear(UsuarioDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs =  null;
        try{
            cs = con.prepareCall(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getNombre());
            cs.setString(2, dto.getEntidad().getPaterno());
            cs.setString(3, dto.getEntidad().getMaterno());
            cs.setString(4, dto.getEntidad().getEmail());
            
            if(dto.getEntidad().getImagen()!= null){
                cs.setBlob(5, dto.getEntidad().getImagen());
            }
            
            cs.setString(6, dto.getEntidad().getNombreUsuario());
            cs.setString(7, dto.getEntidad().getClaveUsuario());
            
            cs.executeUpdate();
            
            
        }finally{
            if(cs != null ){cs.close();}
            if(con != null){con.close();}
        }
    }

    public void actualizar(UsuarioDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs =  null;
        try{
            cs = con.prepareCall(SQL_UPDATE);
            
            cs.setInt(1, dto.getEntidad().getIdUsuario());
            
            cs.setString(2, dto.getEntidad().getNombre());
            cs.setString(3, dto.getEntidad().getPaterno());
            cs.setString(4, dto.getEntidad().getMaterno());
            cs.setString(5, dto.getEntidad().getEmail());
            
            if(dto.getEntidad().getImagen()!= null){
                cs.setBlob(6, dto.getEntidad().getImagen());
            }
            
            cs.setString(7, dto.getEntidad().getNombreUsuario());
            cs.setString(8, dto.getEntidad().getClaveUsuario());
            
            
            cs.executeUpdate();
            
            
        }finally{
            if(cs != null ){cs.close();}
            if(con != null){con.close();}
        }
        
    }


    public void eliminar(UsuarioDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs =  null;
        try{
            cs = con.prepareCall(SQL_DELETE);
            cs.setInt(1, dto.getEntidad().getIdUsuario());
            cs.executeUpdate();
           
        }finally{
            if(cs != null ){cs.close();}
            if(con != null){con.close();}
        }
        
    }
    

    public UsuarioDTO obtenerUno(UsuarioDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs =  null;
        ResultSet rs = null;
        try{
            cs = con.prepareCall(SQL_SELECT_ONE);
            cs.setInt(1, dto.getEntidad().getIdUsuario());
            rs = cs.executeQuery();
            List lista = obtenerResultados(rs);
            if(lista.size() > 0 ){
                return (UsuarioDTO)lista.get(0);
            }else{
                return null;
            }
            
        }finally{
            if(rs != null ){rs.close();}
            if(cs != null ){cs.close();}
            if(con != null){con.close();}
            
        }
    }

    
    public UsuarioDTO buscar(UsuarioDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs =  null;
        ResultSet rs = null;
        try{
            cs = con.prepareCall(SQL_SELECT_NOMBRE);
            cs.setString(1, dto.getEntidad().getNombreUsuario());
            rs = cs.executeQuery();
            List lista = obtenerResultados(rs);
            if(lista.size() > 0 ){
                return (UsuarioDTO)lista.get(0);
            }else{
                return null;
            }
            
        }finally{
            if(rs != null ){rs.close();}
            if(cs != null ){cs.close();}
            if(con != null){con.close();}
            
        }
    }
    
    
    
    
    public List obtenerTodos() throws SQLException{
        obtenerConexion();
        PreparedStatement ps =  null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();
            List lista = obtenerResultados(rs);
            if(lista.size() > 0 ){
                return lista;
            }else{
                return null;
            }
            
        }finally{
            if(rs != null ){rs.close();}
            if(ps != null ){ps.close();}
            if(con != null){con.close();}
            
        }
        
    }

    
    public List obtenerResultados(ResultSet rs) throws SQLException{
        List lista = new ArrayList();
        while(rs.next()){
            UsuarioDTO dto = new UsuarioDTO();
            dto.getEntidad().setIdUsuario(rs.getInt("idUsuario"));
            dto.getEntidad().setNombre(rs.getString("nombre"));
            dto.getEntidad().setPaterno(rs.getString("paterno"));
            dto.getEntidad().setMaterno(rs.getString("materno"));
            dto.getEntidad().setEmail(rs.getString("email"));
            dto.getEntidad().setImagen(rs.getBlob("imagen").getBinaryStream());
            dto.getEntidad().setNombreUsuario(rs.getString("nombreUsuario"));
            dto.getEntidad().setClaveUsuario(rs.getString("claveUsuario"));
            lista.add(dto);
        }
        return lista;
    }


    public void mostrarImagen(int id, HttpServletResponse response) throws SQLException, IOException{
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        response.setContentType("image/*");
        try{
            os = response.getOutputStream();
            ps = con.prepareStatement(SQL_SELECT_ONE);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                is = rs.getBinaryStream("imagen"); 
            }
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(os);
            int  i = 0;
            while((i = bis.read()) != -1 ){
                bos.write(i);
            }
            
        }finally{
            if(rs != null ){rs.close();}
            if(ps != null ){ps.close();}
            if(con != null){con.close();}
            
            
        }
    }

    
    
}
