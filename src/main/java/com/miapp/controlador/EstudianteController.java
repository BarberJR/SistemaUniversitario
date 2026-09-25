package com.miapp.controlador;

import com.miapp.Utilidades.EstadoMatricula;
import com.miapp.modelo.Estudiante;
import com.miapp.servicios.IBuscador;
import com.miapp.vista.EstudianteView;

import java.util.ArrayList;
import java.util.List;


public class EstudianteController implements IBuscador {

    // ── Constantes finales ────────────────────────────────────────────────────
    private static final int CANTIDAD_ESTUDIANTES_INICIALES = 12;
    private static final String MENSAJE_BUSQUEDA_VACIA = "Por favor ingrese un nombre para buscar.";
    private static final String MENSAJE_BUSQUEDA_CARRERA_VACIA = "Por favor seleccione una carrera para buscar.";
    private static final String MENSAJE_SIN_RESULTADOS = "No se encontraron estudiantes con ese criterio.";

    // ── Vista ─────────────────────────────────────────────────────────────────
    private EstudianteView vista;

    // ── Array de estudiantes (fuente de datos) ────────────────────────────────
    private Estudiante[] estudiantes;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteController(EstudianteView vista) {
        this.vista = vista;
        // Primero cargar datos (inicializar estudiantes[])
        cargarDatos();
        // Luego asignar controlador a la vista (ahora es seguro acceder a estudiantes[])
        this.vista.setControlador(this);
    }

    // ── Implementación de la interfaz IBuscador ───────────────────────────────

    @Override
    public void cargarDatos() {
        inicializarEstudiantes();
    }

    @Override
    public void buscarEstudiante(String criterio) {
        buscarPorCriterio(criterio);
    }

    @Override
    public void buscarEstudiantePorCarrera(String carrera) {
        buscarPorCarrera(carrera);
    }

    // ── Carga de datos iniciales ──────────────────────────────────────────────

    private void inicializarEstudiantes() {
        estudiantes = new Estudiante[CANTIDAD_ESTUDIANTES_INICIALES];

        // Reinicia el contador estático de Estudiante antes de cargar nuevos datos
        Estudiante.reiniciarContador();

        estudiantes[0]  = new Estudiante(1,  "Ana ","García",        "Ingeniería de Sistemas",  4.5);
        estudiantes[1]  = new Estudiante(2,  "Carlos"," López",      "Ingeniería Civil",        3.8);
        estudiantes[2]  = new Estudiante(3,  "María", "Rodríguez",   "Medicina",                4.9);
        estudiantes[3]  = new Estudiante(4,  "José ","Martínez",     "Derecho",                 3.5);
        estudiantes[4]  = new Estudiante(5,  "Laura ","Sánchez",     "Administración",          4.1);
        estudiantes[5]  = new Estudiante(6,  "Andrés ","Torres",     "Ingeniería de Sistemas",  3.9);
        estudiantes[6]  = new Estudiante(7,  "Valentina ","Gómez",   "Psicología",              4.3);
        estudiantes[7]  = new Estudiante(8,  "Luis ","Herrera",      "Economía",                3.7);
        estudiantes[8]  = new Estudiante(9,  "Sofía ","Díaz",        "Ingeniería Civil",        4.6);
        estudiantes[9]  = new Estudiante(10, "Juliana ","Morales",   "Medicina",                4.8);
        estudiantes[10] = new Estudiante(11, "Ana Milena ","Ruiz",   "Derecho",                 4.0);
        estudiantes[11] = new Estudiante(12, "Carlos Andrés ","Paz", "Administración",          3.6);

        // Log: informa cuántos estudiantes se cargaron usando static getTotalEstudiantes()
        System.out.println("Total de estudiantes cargados: " + Estudiante.getTotalEstudiantes());
    }

    // ── Lógica de búsqueda ────────────────────────────────────────────────────

  
    private void buscarPorCriterio(String criterio) {

        // Validación básica usando constante final
        if (criterio == null || criterio.isEmpty()) {
            vista.mostrarError(MENSAJE_BUSQUEDA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        String criterioBajo = criterio.toLowerCase();

        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null && (e.getNombre().toLowerCase().contains(criterioBajo) ||
                e.getApellido().toLowerCase().contains(criterioBajo))) {
                resultados.add(e);
            }
        }

        if (resultados.isEmpty()) {
            vista.mostrarEstudiantes(new ArrayList<>()); // mostrará mensaje vacío
        } else if (resultados.size() == 1) {
            // Un solo resultado: usar vista.mostrarEstudiante(fila)
            vista.mostrarEstudiante(convertirAFila(resultados.get(0)));
        } else {
            // Varios resultados: mostrar lista completa ya convertida a filas
            vista.mostrarEstudiantes(convertirAFilas(resultados));
        }
    }

   
    private void buscarPorCarrera(String carrera) {
        // Validación básica usando constante final
        if (carrera == null || carrera.isEmpty() || carrera.equals("Seleccionar...")) {
            vista.mostrarError(MENSAJE_BUSQUEDA_CARRERA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();

        // Búsqueda exacta por carrera
        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null && e.getCarrera().equalsIgnoreCase(carrera)) {
                resultados.add(e);
            }
        }

        // Mostrar resultados (ya convertidos a filas, no como Estudiante)
        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    
    private Object[] convertirAFila(Estudiante e) {
        return new Object[]{
            e.getId(),
            e.getNombre(),
            e.getApellido(),
            e.getCarrera(),
            String.format("%.2f", e.getPromedio())
        };
    }

  
    private List<Object[]> convertirAFilas(List<Estudiante> lista) {
        List<Object[]> filas = new ArrayList<>();
        for (Estudiante e : lista) {
            filas.add(convertirAFila(e));
        }
        return filas;
    }

    public Estudiante obtenerEstudiantePorId(int id) {
        for (Estudiante e : estudiantes) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public String[] obtenerCarrerasUnicas() {
        List<String> carreras = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null) {
                String carrera = e.getCarrera();
                if (!carreras.contains(carrera)) {
                    carreras.add(carrera);
                }
            }
        }
        return carreras.toArray(new String[0]);
    }

 
    public final int obtenerTotalEstudiantes() {
        return Estudiante.getTotalEstudiantes();
    }

   
    public boolean agregarEstudiante(String nombre, String apellido, String carrera, double promedio) {
        // Validación de datos
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() ||
            carrera == null || carrera.isEmpty()) {
            vista.mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        // Expandir el array si es necesario antes de agregar
        if (estudiantes.length == Estudiante.getTotalEstudiantes()) {
            // El array está lleno, crear uno más grande
            Estudiante[] nuevoArray = new Estudiante[estudiantes.length + 5];
            System.arraycopy(estudiantes, 0, nuevoArray, 0, estudiantes.length);
            estudiantes = nuevoArray;
        }

        // Obtener el índice donde se guardará el nuevo estudiante
        int indiceNuevoEstudiante = Estudiante.getTotalEstudiantes();

        // Crear nuevo estudiante con ID automático basado en el contador static
        int proximoId = Estudiante.getProximoId();
        Estudiante nuevoEstudiante = new Estudiante(proximoId, nombre, apellido, carrera, promedio);

        // Agregar el nuevo estudiante en la posición correcta
        estudiantes[indiceNuevoEstudiante] = nuevoEstudiante;

        // Mostrar mensaje de éxito
        vista.mostrarMensaje("Estudiante agregado correctamente.\nTotal de estudiantes: " +
                            Estudiante.getTotalEstudiantes());

        return true;
    }

    public List<Object[]> obtenerFilasEstudiantes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Object[]> obtenerFilasPorEstado(EstadoMatricula estado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int obtenerIdEstudiantePorIndice(int selectedIndex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean inscribirEstudiante(int id, String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean retirarInscripcion(int id, String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int obtenerIdProfesorPorIndice(int selectedIndex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean agregarProfesor(String text, double salario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean asignarProfesor(String codigo, int idProfesor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String[] obtenerEstudiantesParaCombo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String[] obtenerCursos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String[] obtenerProfesores() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Iterable<Object[]> obtenerFilasCursos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Iterable<Object[]> obtenerFilasProfesores() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object obtenerTotalCursos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object obtenerTotalProfesores() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}