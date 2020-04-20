/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.utilerias;

/**
 *
 * @author unityofdisaster
 */
public class Grafica {
    private int cantidad;
    private String nombre;

    public Grafica() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Grafica{" + "cantidad=" + cantidad + ", nombre=" + nombre + '}';
    }
    
}
