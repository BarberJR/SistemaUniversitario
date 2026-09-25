package com.miapp.modelo;

import com.miapp.Utilidades.EstadoMatricula;
import com.miapp.servicios.Inscribible;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Persona implements Inscribible {

    private String carrera;
    private double promedio;

    public static final int MAX_MATERIAS = 7;

    private List<Curso> cursos;
    private EstadoMatricula estadoMatricula;

    private static int totalEstudiantes = 0;
    private static int proximoId = 1;

    public Estudiante(int id, String nombre, String apellido,
            String carrera, double promedio) {

        super(nombre, apellido, id);

        this.carrera = carrera;
        this.promedio = promedio;
        this.cursos = new ArrayList<>();
        this.estadoMatricula = EstadoMatricula.ACTIVO;

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

    public EstadoMatricula getEstadoMatricula() {
        return estadoMatricula;
    }

    public void setEstadoMatricula(EstadoMatricula estadoMatricula) {
        if (estadoMatricula != null) {
            this.estadoMatricula = estadoMatricula;
        }
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

    // Esta es la función que tú querías conservar
    public boolean retirarCurso(Curso curso) {

        if (curso == null || !cursos.contains(curso)) {
            return false;
        }

        cursos.remove(curso);
        curso.quitarEstudiante(this);

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