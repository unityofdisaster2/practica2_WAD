/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.DTO;

import com.ipn.mx.entidades.Carrera;
import java.io.Serializable;

/**
 *
 * @author unityofdisaster
 */
public class CarreraDTO implements Serializable{
    private Carrera entidad;

    public CarreraDTO() {
        entidad = new Carrera();
    }

    public CarreraDTO(Carrera entidad) {
        this.entidad = entidad;
    }

    public Carrera getEntidad() {
        return entidad;
    }

    public void setEntidad(Carrera entidad) {
        this.entidad = entidad;
    }
    
    
}
