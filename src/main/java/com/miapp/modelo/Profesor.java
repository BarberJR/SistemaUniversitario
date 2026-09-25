/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

/**
 *
 * @author Estudiante
 */



public class Profesor extends Persona {

    private final double salarioBase;

      public Profesor(String nombre,String apellido, int id, double salarioBase) {
        super(nombre,apellido, id);
        this.salarioBase = salarioBase;
    }
      
    public double getSalarioBase() {
        return salarioBase;
    }

    public void impartirClase() {
        System.out.println("El profesor " + getNombre() + " está impartiendo clase.");
    }

    @Override
    public double calcularPago() {
        return salarioBase;
    }
}