/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.entidades;

import java.io.InputStream;
import java.io.Serializable;

/**
 *
 * @author unityofdisaster
 */
public class Alumno implements Serializable{
    private int idAlumno;
    private String nombreAlumno;
    private String paternoAlumno;
    private String maternoAlumno;
    private String emailAlumno;
    private InputStream imagen;
    private int idCarrera;

    public Alumno() {
    }
    
    
    
    
    
    
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getPaternoAlumno() {
        return paternoAlumno;
    }

    public void setPaternoAlumno(String paternoAlumno) {
        this.paternoAlumno = paternoAlumno;
    }

    public String getMaternoAlumno() {
        return maternoAlumno;
    }

    public void setMaternoAlumno(String maternoAlumno) {
        this.maternoAlumno = maternoAlumno;
    }

    public String getEmailAlumno() {
        return emailAlumno;
    }

    public void setEmailAlumno(String emailAlumno) {
        this.emailAlumno = emailAlumno;
    }

    public InputStream getImagen() {
        return imagen;
    }

    public void setImagen(InputStream imagen) {
        this.imagen = imagen;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    @Override
    public String toString() {
        return "Alumno{" + "idAlumno=" + idAlumno + ", nombreAlumno=" + nombreAlumno + ", paternoAlumno=" + paternoAlumno + ", maternoAlumno=" + maternoAlumno + ", emailAlumno=" + emailAlumno + ", imagen=" + imagen + ", idCarrera=" + idCarrera + '}';
    }
    
    
    
}
