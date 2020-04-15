/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.DTO;

import com.ipn.mx.entidades.Alumno;
import java.io.Serializable;

/**
 *
 * @author unityofdisaster
 */
public class AlumnoDTO implements Serializable{
    private Alumno entidad;

    public AlumnoDTO() {
        entidad = new Alumno();
    }

    public AlumnoDTO(Alumno entidad) {
        this.entidad = entidad;
    }

    public Alumno getEntidad() {
        return entidad;
    }

    public void setEntidad(Alumno entidad) {
        this.entidad = entidad;
    }
    
    
}
