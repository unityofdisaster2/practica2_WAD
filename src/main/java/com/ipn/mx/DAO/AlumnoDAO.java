/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.DAO;

import com.ipn.mx.DTO.AlumnoDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author unityofdisaster
 */
public class AlumnoDAO {

    private static final String SQL_INSERT = "{CALL crear_alumno(?, ?, ?, ?, ?, ?)}";
    private static final String SQL_UPDATE = "{CALL actualizar_alumno(?, ?, ?, ?, ?, ?, ?)}";
    private static final String SQL_DELETE = "{CALL borrar_alumno(?)}";
    private static final String SQL_SELECT_ONE = "{CALL obtener_un_alumno(?)}";
    private static final String SQL_SELECT_ALL = "{CALL obtener_alumnos()}";

    
    
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
    
    

    public void crear(AlumnoDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        try{
            cs = con.prepareCall(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getNombreAlumno());
            cs.setString(2, dto.getEntidad().getPaternoAlumno());
            cs.setString(3, dto.getEntidad().getMaternoAlumno());
            cs.setString(4, dto.getEntidad().getEmailAlumno());
            if(dto.getEntidad().getImagen() != null){
                cs.setBlob(5, dto.getEntidad().getImagen());
            }
            cs.setInt(6, dto.getEntidad().getIdCarrera());
            cs.executeUpdate();
            
        }finally{
            if(cs != null){ cs.close(); }
            if(con != null){ cs.close(); }
        }
    }

    public void eliminar(AlumnoDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        try{
            cs = con.prepareCall(SQL_DELETE);
            cs.setInt(1, dto.getEntidad().getIdAlumno());
            cs.executeUpdate();
            
        }finally{
            if(cs != null){ cs.close(); }
            if(con != null){ cs.close(); }
        }
    }
    
    public void actualizar(AlumnoDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        try{
            cs = con.prepareCall(SQL_UPDATE);
            cs.setInt(1, dto.getEntidad().getIdAlumno());
            cs.setString(2, dto.getEntidad().getNombreAlumno());
            cs.setString(3, dto.getEntidad().getPaternoAlumno());
            cs.setString(4, dto.getEntidad().getMaternoAlumno());
            cs.setString(5, dto.getEntidad().getEmailAlumno());

            if(dto.getEntidad().getImagen() != null){
                cs.setBlob(6, dto.getEntidad().getImagen());
            }



            cs.setInt(7, dto.getEntidad().getIdCarrera());
            cs.executeUpdate();
            
        }finally{
            if(cs != null){ cs.close(); }
            if(con != null){ cs.close(); }
        }
    }
    

    
    
    public List obtenerTodos() throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        try{
            cs = con.prepareCall(SQL_SELECT_ALL);
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if(resultados.size() > 0){
                return resultados;
            }else{
                return null;
            }            
        }finally{
            if( rs != null) rs.close();
            if(cs != null){ cs.close(); }
            if(con != null){ cs.close(); }            
        }
    }

    public AlumnoDTO obtenerUno(AlumnoDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        try{
            cs = con.prepareCall(SQL_SELECT_ONE);
            cs.setInt(1, dto.getEntidad().getIdAlumno());
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if(resultados.size() > 0){
                return (AlumnoDTO)resultados.get(0);
            }else{
                return null;
            }            
        }finally{
            if( rs != null) rs.close();
            if(cs != null){ cs.close(); }
            if(con != null){ cs.close(); }            
        }
    }

    private List obtenerResultados(ResultSet rs) throws SQLException{
        List resultados = new ArrayList();
        while(rs.next()){
            AlumnoDTO dto = new AlumnoDTO();
            dto.getEntidad().setIdAlumno(rs.getInt("idAlumno"));
            dto.getEntidad().setNombreAlumno(rs.getString("nombreAlumno"));
            dto.getEntidad().setPaternoAlumno(rs.getString("paternoAlumno"));
            dto.getEntidad().setMaternoAlumno(rs.getString("maternoAlumno"));
            dto.getEntidad().setEmailAlumno(rs.getString("emailAlumno"));
            dto.getEntidad().setImagen(rs.getBlob("imagen").getBinaryStream());
            dto.getEntidad().setIdCarrera(rs.getInt("idCarrera"));
            resultados.add(dto);
        }
        return resultados;
    }


    
}
