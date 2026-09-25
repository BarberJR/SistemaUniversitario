package com.miapp.controlador;

import com.miapp.Utilidades.EstadoMatricula;
import com.miapp.modelo.Curso;
import com.miapp.modelo.Estudiante;
import com.miapp.modelo.Profesor;
import com.miapp.vista.EstudianteView;

import java.util.ArrayList;
import java.util.List;

public class EstudianteController {

    private static final int CANTIDAD_ESTUDIANTES_INICIALES = 12;

    private static final String MENSAJE_BUSQUEDA_VACIA =
            "Por favor ingrese un nombre para buscar.";

    private static final String MENSAJE_BUSQUEDA_CARRERA_VACIA =
            "Por favor seleccione una carrera para buscar.";

    private final EstudianteView vista;

    private Estudiante[] estudiantes;
    private Curso[] cursos;
    private Profesor[] profesores;

    public EstudianteController(EstudianteView vista) {

        this.vista = vista;

        cargarDatos();

        this.vista.setControlador(this);
    }

    public void cargarDatos() {

        inicializarEstudiantes();
        inicializarCursos();
        inicializarProfesores();
        crearInscripcionesIniciales();
    }

    // ---------------- ESTUDIANTES ----------------

    private void inicializarEstudiantes() {

        estudiantes = new Estudiante[CANTIDAD_ESTUDIANTES_INICIALES];

        Estudiante.reiniciarContador();

        estudiantes[0] = new Estudiante(
                1, "Ana", "García",
                "Ingeniería de Sistemas", 4.5);

        estudiantes[1] = new Estudiante(
                2, "Carlos", "López",
                "Ingeniería Civil", 3.8);

        estudiantes[2] = new Estudiante(
                3, "María", "Rodríguez",
                "Medicina", 4.9);

        estudiantes[3] = new Estudiante(
                4, "José", "Martínez",
                "Derecho", 3.5);

        estudiantes[4] = new Estudiante(
                5, "Laura", "Sánchez",
                "Administración", 4.1);

        estudiantes[5] = new Estudiante(
                6, "Andrés", "Torres",
                "Ingeniería de Sistemas", 3.9);

        estudiantes[6] = new Estudiante(
                7, "Valentina", "Gómez",
                "Psicología", 4.3);

        estudiantes[7] = new Estudiante(
                8, "Luis", "Herrera",
                "Economía", 3.7);

        estudiantes[8] = new Estudiante(
                9, "Sofía", "Díaz",
                "Ingeniería Civil", 4.6);

        estudiantes[9] = new Estudiante(
                10, "Juliana", "Morales",
                "Medicina", 4.8);

        estudiantes[10] = new Estudiante(
                11, "Ana Milena", "Ruiz",
                "Derecho", 4.0);

        estudiantes[11] = new Estudiante(
                12, "Carlos Andrés", "Paz",
                "Administración", 3.6);
    }

    // ---------------- CURSOS ----------------

    private void inicializarCursos() {

        cursos = new Curso[]{
            new Curso("PROG101", 3),
            new Curso("MAT101", 3),
            new Curso("POO201", 4),
            new Curso("BD301", 3),
            new Curso("RED201", 3),
            new Curso("ING101", 2)
        };
    }

    // ---------------- PROFESORES ----------------

    private void inicializarProfesores() {

        profesores = new Profesor[]{
            new Profesor("Laura", "Gómez", 101, 3200000),
            new Profesor("Carlos", "Pérez", 102, 3500000),
            new Profesor("Mónica", "Torres", 103, 3400000)
        };
    }

    // Algunas inscripciones para que el programa no aparezca vacío

    private void crearInscripcionesIniciales() {

        estudiantes[0].inscribir(cursos[0]);
        estudiantes[0].inscribir(cursos[2]);

        estudiantes[1].inscribir(cursos[0]);
        estudiantes[1].inscribir(cursos[1]);

        estudiantes[2].inscribir(cursos[1]);
        estudiantes[2].inscribir(cursos[3]);

        cursos[0].setProfesor(profesores[0]);
        cursos[1].setProfesor(profesores[1]);
        cursos[2].setProfesor(profesores[2]);
    }

    // ---------------- BÚSQUEDA ----------------

    public void buscarEstudiante(String criterio) {

        if (criterio == null || criterio.trim().isEmpty()) {

            vista.mostrarError(MENSAJE_BUSQUEDA_VACIA);

            return;
        }

        List<Estudiante> resultados = new ArrayList<>();

        String criterioBuscado =
                criterio.trim().toLowerCase();

        for (Estudiante estudiante : estudiantes) {

            if (estudiante != null
                    && (estudiante.getNombre()
                            .toLowerCase()
                            .contains(criterioBuscado)

                    || estudiante.getApellido()
                            .toLowerCase()
                            .contains(criterioBuscado))) {

                resultados.add(estudiante);
            }
        }

        vista.mostrarEstudiantes(
                convertirAFilas(resultados));
    }

    public void buscarEstudiantePorCarrera(String carrera) {

        if (carrera == null
                || carrera.trim().isEmpty()
                || carrera.equals("Seleccionar...")) {

            vista.mostrarError(
                    MENSAJE_BUSQUEDA_CARRERA_VACIA);

            return;
        }

        List<Estudiante> resultados =
                new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {

            if (estudiante != null
                    && estudiante.getCarrera()
                            .equalsIgnoreCase(carrera)) {

                resultados.add(estudiante);
            }
        }

        vista.mostrarEstudiantes(
                convertirAFilas(resultados));
    }

    // ---------------- TABLA ESTUDIANTES ----------------

    private Object[] convertirAFila(
            Estudiante estudiante) {

        return new Object[]{
            estudiante.getId(),
            estudiante.getNombre(),
            estudiante.getApellido(),
            estudiante.getCarrera(),
            String.format("%.2f",
                    estudiante.getPromedio()),
            estudiante.getEstadoMatricula()
        };
    }

    private List<Object[]> convertirAFilas(
            List<Estudiante> lista) {

        List<Object[]> filas =
                new ArrayList<>();

        for (Estudiante estudiante : lista) {

            filas.add(convertirAFila(estudiante));
        }

        return filas;
    }

    public List<Object[]> obtenerFilasEstudiantes() {

        List<Object[]> filas =
                new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {

            if (estudiante != null) {

                filas.add(convertirAFila(estudiante));
            }
        }

        return filas;
    }

    public List<Object[]> obtenerFilasPorEstado(
            EstadoMatricula estado) {

        List<Object[]> filas =
                new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {

            if (estudiante != null
                    && (estado == null
                    || estudiante.getEstadoMatricula()
                            == estado)) {

                filas.add(convertirAFila(estudiante));
            }
        }

        return filas;
    }

    // ---------------- BUSCAR ESTUDIANTE POR ID ----------------

    public Estudiante obtenerEstudiantePorId(int id) {

        for (Estudiante estudiante : estudiantes) {

            if (estudiante != null
                    && estudiante.getId() == id) {

                return estudiante;
            }
        }

        return null;
    }

    public int obtenerIdEstudiantePorIndice(int indice) {

        int posicion = 0;

        for (Estudiante estudiante : estudiantes) {

            if (estudiante != null) {

                if (posicion == indice) {

                    return estudiante.getId();
                }

                posicion++;
            }
        }

        return -1;
    }

    // ---------------- CARRERAS ----------------

    public String[] obtenerCarrerasUnicas() {

        List<String> carreras =
                new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {

            if (estudiante != null) {

                String carrera =
                        estudiante.getCarrera();

                if (!carreras.contains(carrera)) {

                    carreras.add(carrera);
                }
            }
        }

        return carreras.toArray(
                new String[0]);
    }

    // ---------------- AGREGAR ESTUDIANTE ----------------

    public boolean agregarEstudiante(
            String nombre,
            String apellido,
            String carrera,
            double promedio) {

        if (nombre == null
                || nombre.trim().isEmpty()
                || apellido == null
                || apellido.trim().isEmpty()
                || carrera == null
                || carrera.trim().isEmpty()) {

            vista.mostrarError(
                    "Todos los campos son obligatorios.");

            return false;
        }

        if (estudiantes.length
                == Estudiante.getTotalEstudiantes()) {

            Estudiante[] nuevoArray =
                    new Estudiante[
                        estudiantes.length + 5];

            System.arraycopy(
                    estudiantes,
                    0,
                    nuevoArray,
                    0,
                    estudiantes.length);

            estudiantes = nuevoArray;
        }

        int indice =
                Estudiante.getTotalEstudiantes();

        int nuevoId =
                Estudiante.getProximoId();

        Estudiante nuevoEstudiante =
                new Estudiante(
                        nuevoId,
                        nombre.trim(),
                        apellido.trim(),
                        carrera.trim(),
                        promedio);

        estudiantes[indice] =
                nuevoEstudiante;

        vista.mostrarMensaje(
                "Estudiante agregado correctamente.\n"
                + "Total de estudiantes: "
                + Estudiante.getTotalEstudiantes());

        return true;
    }

    // ---------------- INSCRIBIR ----------------

    public boolean inscribirEstudiante(
            int id,
            String codigo) {

        Estudiante estudiante =
                obtenerEstudiantePorId(id);

        Curso curso =
                obtenerCursoPorCodigo(codigo);

        if (estudiante == null
                || curso == null) {

            vista.mostrarError(
                    "No se encontró el estudiante o el curso.");

            return false;
        }

        if (!estudiante.inscribir(curso)) {

            vista.mostrarError(
                    "No se pudo realizar la inscripción."
                    + "\nVerifique el límite de materias"
                    + " o si ya está inscrito.");

            return false;
        }

        vista.mostrarMensaje(
                "Curso inscrito correctamente.");

        return true;
    }

    // ---------------- RETIRAR CURSO ----------------

    public boolean retirarInscripcion(
            int id,
            String codigo) {

        Estudiante estudiante =
                obtenerEstudiantePorId(id);

        Curso curso =
                obtenerCursoPorCodigo(codigo);

        if (estudiante == null
                || curso == null) {

            vista.mostrarError(
                    "No se encontró el estudiante o el curso.");

            return false;
        }

        if (!estudiante.retirarCurso(curso)) {

            vista.mostrarError(
                    "El estudiante no está inscrito "
                    + "en ese curso.");

            return false;
        }

        vista.mostrarMensaje(
                "Curso retirado correctamente.");

        return true;
    }

    private Curso obtenerCursoPorCodigo(
            String codigo) {

        if (codigo == null) {
            return null;
        }

        for (Curso curso : cursos) {

            if (curso.getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return curso;
            }
        }

        return null;
    }

    // ---------------- COMBOS ----------------

    public String[] obtenerEstudiantesParaCombo() {

        List<String> lista =
                new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {

            if (estudiante != null) {

                lista.add(
                        estudiante.getId()
                        + " - "
                        + estudiante.getNombre()
                        + " "
                        + estudiante.getApellido());
            }
        }

        return lista.toArray(
                new String[0]);
    }

    public String[] obtenerCursos() {

        String[] resultado =
                new String[cursos.length];

        for (int i = 0;
                i < cursos.length;
                i++) {

            resultado[i] =
                    cursos[i].getCodigo();
        }

        return resultado;
    }

    public String[] obtenerProfesores() {

        String[] resultado =
                new String[profesores.length];

        for (int i = 0;
                i < profesores.length;
                i++) {

            resultado[i] =
                    profesores[i].getId()
                    + " - "
                    + profesores[i].getNombre()
                    + " "
                    + profesores[i].getApellido();
        }

        return resultado;
    }

    // ---------------- TABLA CURSOS ----------------

    public List<Object[]> obtenerFilasCursos() {

        List<Object[]> filas =
                new ArrayList<>();

        for (Curso curso : cursos) {

            String profesor =
                    "Sin asignar";

            if (curso.getProfesor() != null) {

                profesor =
                        curso.getProfesor().getNombre()
                        + " "
                        + curso.getProfesor().getApellido();
            }

            filas.add(new Object[]{
                curso.getCodigo(),
                curso.getCreditos(),
                curso.getEstudiantes().size(),
                profesor
            });
        }

        return filas;
    }

    // ---------------- PROFESORES ----------------

    public List<Object[]> obtenerFilasProfesores() {

        List<Object[]> filas =
                new ArrayList<>();

        for (Profesor profesor : profesores) {

            filas.add(new Object[]{
                profesor.getId(),
                profesor.getNombre(),
                profesor.getApellido(),
                profesor.getSalarioBase()
            });
        }

        return filas;
    }

    public int obtenerIdProfesorPorIndice(
            int indice) {

        if (indice >= 0
                && indice < profesores.length) {

            return profesores[indice].getId();
        }

        return -1;
    }

    public boolean agregarProfesor(
            String nombre,
            double salario) {

        if (nombre == null
                || nombre.trim().isEmpty()) {

            vista.mostrarError(
                    "El nombre del profesor es obligatorio.");

            return false;
        }

        Profesor[] nuevoArray =
                new Profesor[
                    profesores.length + 1];

        System.arraycopy(
                profesores,
                0,
                nuevoArray,
                0,
                profesores.length);

        int nuevoId =
                100 + profesores.length + 1;

        nuevoArray[profesores.length] =
                new Profesor(
                        nombre.trim(),
                        "",
                        nuevoId,
                        salario);

        profesores = nuevoArray;

        vista.mostrarMensaje(
                "Profesor agregado correctamente.");

        return true;
    }

    public boolean asignarProfesor(
            String codigo,
            int idProfesor) {

        Curso curso =
                obtenerCursoPorCodigo(codigo);

        Profesor profesor =
                obtenerProfesorPorId(idProfesor);

        if (curso == null
                || profesor == null) {

            vista.mostrarError(
                    "No se encontró el curso o el profesor.");

            return false;
        }

        curso.setProfesor(profesor);

        vista.mostrarMensaje(
                "Profesor asignado correctamente.");

        return true;
    }

    private Profesor obtenerProfesorPorId(
            int id) {

        for (Profesor profesor : profesores) {

            if (profesor.getId() == id) {

                return profesor;
            }
        }

        return null;
    }

    // ---------------- TOTALES ----------------

    public int obtenerTotalEstudiantes() {

        return Estudiante.getTotalEstudiantes();
    }

    public int obtenerTotalCursos() {

        return cursos.length;
    }

    public int obtenerTotalProfesores() {

        return profesores.length;
    }
}