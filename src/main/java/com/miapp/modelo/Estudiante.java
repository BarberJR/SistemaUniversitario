package com.miapp.modelo;

/**
 * Modelo: representa la entidad Estudiante.
 */

import java.util.ArrayList;
import java.util.List;
import com.miapp.servicios.Inscribible;

public class Estudiante extends Persona implements Inscribible {

    private double promedio;

    public static final int MAX_MATERIAS = 6;

    private List<Curso> cursos;

    public Estudiante(String nombre, int id, double promedio) {
        super(nombre, id);
        this.promedio = promedio;
        this.cursos = new ArrayList<>();
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    @Override
    public boolean inscribir(Curso curso) {

        if (curso == null) {
            return false;
        }

        if (cursos.size() >= MAX_MATERIAS) {
            return false;
        }

        if (cursos.contains(curso)) {
            return false;
        }

        cursos.add(curso);
        curso.agregarEstudiante(this);

        return true;
    }

    @Override
    public double calcularPago() {
        return 0;
    }
}