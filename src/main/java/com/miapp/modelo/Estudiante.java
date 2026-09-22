package com.miapp.modelo;

/**
 * Modelo: representa la entidad Estudiante.
 */

import com.miapp.servicios.Inscribible;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Persona implements Inscribible {


    private String carrera;
    private double promedio;

    public static final int MAX_MATERIAS = 7;

    private List<Curso> cursos;

    private static int totalEstudiantes = 0;
    private static int proximoId = 1;

    public Estudiante(int id, String nombre, String apellido,
                      String carrera, double promedio) {

        super(nombre,apellido, id);
        
        this.carrera = carrera;
        this.promedio = promedio;

        this.cursos = new ArrayList<>();

        totalEstudiantes++;

        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }


    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
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

    public static int getTotalEstudiantes() {
        return totalEstudiantes;
    }

    public static int getProximoId() {
        return proximoId;
    }

    public static void reiniciarContador() {
        totalEstudiantes = 0;
        proximoId = 1;
    }
}