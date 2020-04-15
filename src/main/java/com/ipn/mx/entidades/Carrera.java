/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.entidades;

import java.io.Serializable;

/**
 *
 * @author unityofdisaster
 */
public class Carrera implements Serializable{
    private int idCarrera;
    private String nombreCarrera;
    private String descripcion;
    private int duracion;

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Carrera() {
    }
    
    
    @Override
    public String toString() {
        return "Carrera{" + "idCarrera=" + idCarrera + ", nombreCarrera=" + nombreCarrera + ", descripcion=" + descripcion + ", duracion=" + duracion + '}';
    }
    
}
