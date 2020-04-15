/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.DAO;

import com.ipn.mx.DTO.CarreraDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author unityofdisaster
 */
public class CarreraDAO {

    private static final String SQL_INSERT = "{CALL crear_carrera(?, ?, ?)}";
    private static final String SQL_UPDATE = "{CALL actualizar_carrera(?, ?, ?, ?)}";
    private static final String SQL_DELETE = "{CALL borrar_carrera(?)}";
    private static final String SQL_SELECT = "{CALL obtener_una_carrera(?)}";
    private static final String SQL_SELECT_ALL = "{CALL obtener_carreras()}";
    // private static final String SQL_GRAFICAS ="{CALL spDatosGrafica()}";

    private Connection con;

    private void obtenerConexion() {
        try {
            Context ic = new InitialContext();
            //es el nodo en el arbol JNDI donde se pueden encontrar propiedades para el componente JAVA EE actual 
            Context ec = (Context) ic.lookup("java:comp/env");
            DataSource ds = (DataSource) ec.lookup("jdbc/Practica2");
            con = ds.getConnection();

        } catch (NamingException | SQLException ex) {
            Logger.getLogger(CarreraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection conecta() {
        try {
            Context ic = new InitialContext();
            //es el nodo en el arbol JNDI donde se pueden encontrar propiedades para el componente JAVA EE actual 
            Context ec = (Context) ic.lookup("java:comp/env");
            DataSource ds = (DataSource) ec.lookup("jdbc/Practica2");
            con = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

    
    
    public void crear(CarreraDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        try{
            cs = con.prepareCall(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getNombreCarrera());
            cs.setString(2, dto.getEntidad().getDescripcion());
            cs.setInt(3, dto.getEntidad().getDuracion());
            cs.executeUpdate();
            
        }finally{
            if(cs != null){ cs.close(); }
            if(con != null){ cs.close(); }
        }
    }

    public void eliminar(CarreraDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        try{
            cs = con.prepareCall(SQL_DELETE);
            cs.setInt(1, dto.getEntidad().getIdCarrera());
            cs.executeUpdate();
            
        }finally{
            if(cs != null){ cs.close(); }
            if(con != null){ cs.close(); }
        }
    }

    public void actualizar(CarreraDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        try{
            cs = con.prepareCall(SQL_UPDATE);
            cs.setInt(1, dto.getEntidad().getIdCarrera());
            cs.setString(2, dto.getEntidad().getNombreCarrera());
            cs.setString(3, dto.getEntidad().getDescripcion());
            cs.setInt(4, dto.getEntidad().getDuracion());
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

    public CarreraDTO obtenerUno(CarreraDTO dto) throws SQLException{
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        try{
            cs = con.prepareCall(SQL_SELECT);
            rs = cs.executeQuery();
            List resultados = obtenerResultados(rs);
            if(resultados.size() > 0){
                return (CarreraDTO)resultados.get(0);
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
            CarreraDTO dto = new CarreraDTO();
            dto.getEntidad().setIdCarrera(rs.getInt("idCarrera"));
            dto.getEntidad().setNombreCarrera(rs.getString("nombreCarrera"));
            dto.getEntidad().setDescripcion(rs.getString("descripcion"));
            dto.getEntidad().setDuracion(rs.getInt("duracion"));
            
            resultados.add(dto);
        }
        return resultados;
    }
    
    
    
    
    
}
