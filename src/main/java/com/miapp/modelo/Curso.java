/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.miapp.modelo;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private String codigo;
    private int creditos;

    private List<Estudiante> estudiantes;

    private Profesor profesor;

    public Curso(String codigo, int creditos) {
        this.codigo = codigo;
        this.creditos = creditos;
        this.estudiantes = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void agregarEstudiante(Estudiante estudiante) {

        if (estudiante != null && !estudiantes.contains(estudiante)) {
            estudiantes.add(estudiante);
        }
    }

    public void quitarEstudiante(Estudiante estudiante) {
        estudiantes.remove(estudiante);
    }
}