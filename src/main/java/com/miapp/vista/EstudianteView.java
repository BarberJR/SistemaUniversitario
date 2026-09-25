package com.miapp.vista;

import com.miapp.controlador.EstudianteController;
import com.miapp.Utilidades.EstadoMatricula;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class EstudianteView extends JFrame {

    private final JTextField txtBuscar;
    private final JComboBox<String> cmbCarrera;
    private final JComboBox<String> cmbEstado;
    private final JComboBox<String> cmbEstudiante;
    private final JComboBox<String> cmbCurso;
    private final JComboBox<String> cmbProfesor;
    private final JTextField txtNombre;
    private final JTextField txtApellido;
    private final JComboBox<String> cmbNuevaCarrera;
    private final JSpinner spPromedio;
    private final JTextField txtProfesor;
    private final JSpinner spSalario;

    private final JTable tablaEstudiantes;
    private final JTable tablaCursos;
    private final JTable tablaProfesores;
    private final DefaultTableModel modeloEstudiantes;
    private final DefaultTableModel modeloCursos;
    private final DefaultTableModel modeloProfesores;

    private final JLabel lblTotalEstudiantes;
    private final JLabel lblTotalCursos;
    private final JLabel lblTotalProfesores;
    private final JLabel lblEstado;

  
    private static final Color AZUL = new Color(30, 64, 175);
    private static final Color AZUL_CLARO = new Color(239, 246, 255);
    private static final Color FONDO = new Color(245, 247, 250);
    private static final Color BLANCO = Color.WHITE;
    private static final Color TEXTO = new Color(31, 41, 55);
    private static final Color SECUNDARIO = new Color(107, 114, 128);
    private static final Color VERDE = new Color(22, 163, 74);
    private static final Color NARANJA = new Color(234, 88, 12);
    private static final Color ROJO = new Color(220, 38, 38);

  

   private EstudianteController controlador;

    public EstudianteView() {
        aplicarEstiloSistema();

        txtBuscar = new JTextField();
        cmbCarrera = new JComboBox<>();
        cmbEstado = new JComboBox<>();
        cmbEstudiante = new JComboBox<>();
        cmbCurso = new JComboBox<>();
        cmbProfesor = new JComboBox<>();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        cmbNuevaCarrera = new JComboBox<>();
        spPromedio = new JSpinner(new SpinnerNumberModel(3.0, 0.0, 5.0, 0.1));
        txtProfesor = new JTextField();
        spSalario = new JSpinner(new SpinnerNumberModel(3000000.0, 1.0, 99999999.0, 100000.0));

        modeloEstudiantes = crearModelo(new String[]{"ID", "Nombre", "Apellido", "Carrera", "Promedio", "Estado"});
        modeloCursos = crearModelo(new String[]{"Código", "Créditos", "Inscritos", "Profesor"});
        modeloProfesores = crearModelo(new String[]{"ID", "Profesor", "Salario base"});

        tablaEstudiantes = crearTabla(modeloEstudiantes);
        tablaCursos = crearTabla(modeloCursos);
        tablaProfesores = crearTabla(modeloProfesores);

        lblTotalEstudiantes = crearDato("0");
        lblTotalCursos = crearDato("0");
        lblTotalProfesores = crearDato("0");
        lblEstado = new JLabel("Listo para trabajar");

        configurarVentana();
        construirInterfaz();
        configurarEventos();
    }

    private void configurarVentana() {
        setTitle("CampusAdmin | Gestión Universitaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 760);
        setMinimumSize(new Dimension(1050, 680));
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(FONDO);
        raiz.setBorder(new EmptyBorder(0, 0, 0, 0));

        raiz.add(crearEncabezado(), BorderLayout.NORTH);
        raiz.add(crearContenido(), BorderLayout.CENTER);
        raiz.add(crearBarraEstado(), BorderLayout.SOUTH);

        setContentPane(raiz);
    }

    private JPanel crearEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AZUL);
        header.setBorder(new EmptyBorder(22, 30, 22, 30));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("CampusAdmin");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));

        JLabel subtitulo = new JLabel("Sistema universitario · Arquitectura MVC");
        subtitulo.setForeground(new Color(219, 234, 254));
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(5));
        textos.add(subtitulo);

        JLabel version = new JLabel("GESTIÓN ACADÉMICA");
        version.setForeground(new Color(191, 219, 254));
        version.setFont(new Font("SansSerif", Font.BOLD, 12));

        header.add(textos, BorderLayout.WEST);
        header.add(version, BorderLayout.EAST);
        return header;
    }

    private JPanel crearContenido() {
        JPanel contenedor = new JPanel(new BorderLayout(18, 18));
        contenedor.setBackground(FONDO);
        contenedor.setBorder(new EmptyBorder(18, 24, 18, 24));

        JPanel superior = new JPanel(new BorderLayout(15, 15));
        superior.setOpaque(false);
        superior.add(crearTarjetas(), BorderLayout.NORTH);
        superior.add(crearBuscador(), BorderLayout.CENTER);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabs.addTab("Estudiantes", crearPanelEstudiantes());
        tabs.addTab("Cursos e inscripciones", crearPanelCursos());
        tabs.addTab("Profesores", crearPanelProfesores());

        contenedor.add(superior, BorderLayout.NORTH);
        contenedor.add(tabs, BorderLayout.CENTER);
        return contenedor;
    }

    private JPanel crearTarjetas() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 14, 0));
        panel.setOpaque(false);
        panel.add(crearTarjeta("ESTUDIANTES", lblTotalEstudiantes, AZUL));
        panel.add(crearTarjeta("CURSOS", lblTotalCursos, VERDE));
        panel.add(crearTarjeta("PROFESORES", lblTotalProfesores, NARANJA));
        return panel;
    }

    private JPanel crearTarjeta(String titulo, JLabel dato, Color color) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(229, 231, 235)),
                new EmptyBorder(12, 16, 12, 16)));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(SECUNDARIO);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 11));

        dato.setForeground(color);
        dato.setFont(new Font("SansSerif", Font.BOLD, 25));

        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(dato, BorderLayout.CENTER);
        return tarjeta;
    }

    private JPanel crearBuscador() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(229, 231, 235)),
                new EmptyBorder(12, 16, 12, 16)));

        JLabel titulo = new JLabel("Buscar estudiantes");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        titulo.setForeground(TEXTO);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        txtBuscar.setPreferredSize(new Dimension(230, 34));
        cmbCarrera.setPreferredSize(new Dimension(210, 34));
        cmbEstado.setPreferredSize(new Dimension(150, 34));

        c.gridx = 0;
        c.weightx = 1;
        campos.add(txtBuscar, c);
        c.gridx = 1;
        campos.add(cmbCarrera, c);
        c.gridx = 2;
        campos.add(cmbEstado, c);

        JButton buscar = boton("Buscar", AZUL);
        JButton limpiar = boton("Limpiar", new Color(75, 85, 99));
        c.gridx = 3;
        c.weightx = 0;
        campos.add(buscar, c);
        c.gridx = 4;
        campos.add(limpiar, c);

        JButton listar = boton("Ver todos", VERDE);
        c.gridx = 5;
        campos.add(listar, c);

        panel.add(titulo, BorderLayout.WEST);
        panel.add(campos, BorderLayout.CENTER);

        buscar.addActionListener(e -> {
            if (cmbEstado.getSelectedIndex() > 0) {
                EstadoMatricula estado = EstadoMatricula.valueOf((String) cmbEstado.getSelectedItem());
                mostrarEstudiantes(controlador.obtenerFilasPorEstado(estado));
            } else if (!txtBuscar.getText().trim().isEmpty()) {
                controlador.buscarEstudiante(txtBuscar.getText().trim());
            } else if (cmbCarrera.getSelectedIndex() > 0) {
                controlador.buscarEstudiantePorCarrera((String) cmbCarrera.getSelectedItem());
            } else {
                mostrarEstudiantes(controlador.obtenerFilasEstudiantes());
            }
        });

        limpiar.addActionListener(e -> {
            txtBuscar.setText("");
            cmbCarrera.setSelectedIndex(0);
            cmbEstado.setSelectedIndex(0);
            mostrarEstudiantes(controlador.obtenerFilasEstudiantes());
        });

        listar.addActionListener(e -> mostrarEstudiantes(controlador.obtenerFilasEstudiantes()));
        return panel;
    }

    private JPanel crearPanelEstudiantes() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(FONDO);
        panel.setBorder(new EmptyBorder(15, 5, 5, 5));

        panel.add(crearFormularioEstudiante(), BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearFormularioEstudiante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(229, 231, 235)),
                new EmptyBorder(14, 16, 14, 16)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JLabel titulo = new JLabel("Registrar estudiante");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        titulo.setForeground(TEXTO);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(titulo, c);

        c.gridwidth = 1;
        c.gridy = 1;
        c.gridx = 0;
        panel.add(label("Nombre"), c);
        c.gridx = 1;
        panel.add(label("Apellido"), c);
        c.gridx = 2;
        panel.add(label("Carrera"), c);
        c.gridx = 3;
        panel.add(label("Promedio"), c);
        c.gridx = 4;
        panel.add(new JLabel(), c);

        c.gridy = 2;
        c.gridx = 0;
        panel.add(txtNombre, c);
        c.gridx = 1;
        panel.add(txtApellido, c);
        c.gridx = 2;
        panel.add(cmbNuevaCarrera, c);
        c.gridx = 3;
        panel.add(spPromedio, c);

        JButton agregar = boton("+ Agregar estudiante", AZUL);
        c.gridx = 4;
        c.weightx = 0;
        panel.add(agregar, c);

        agregar.addActionListener(e -> agregarEstudiante());
        return panel;
    }

    private JPanel crearPanelCursos() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(FONDO);
        panel.setBorder(new EmptyBorder(15, 5, 5, 5));

        JPanel acciones = new JPanel(new GridBagLayout());
        acciones.setBackground(BLANCO);
        acciones.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(229, 231, 235)),
                new EmptyBorder(12, 16, 12, 16)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 5, 4, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; acciones.add(label("Estudiante"), c);
        c.gridx = 1; c.weightx = 1; acciones.add(cmbEstudiante, c);
        c.gridx = 2; c.weightx = 0; acciones.add(label("Curso"), c);
        c.gridx = 3; c.weightx = 1; acciones.add(cmbCurso, c);
        c.gridx = 4; c.weightx = 0;
        JButton inscribir = boton("Inscribir", VERDE);
        acciones.add(inscribir, c);

        c.gridx = 5;
        JButton retirar = boton("Retirar", ROJO);
        acciones.add(retirar, c);

        c.gridx = 6;
        JButton refrescar = boton("Actualizar", AZUL);
        acciones.add(refrescar, c);

        inscribir.addActionListener(e -> inscribir());
        retirar.addActionListener(e -> retirarInscripcion());
        refrescar.addActionListener(e -> mostrarCursos());

        panel.add(acciones, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaCursos), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelProfesores() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(FONDO);
        panel.setBorder(new EmptyBorder(15, 5, 5, 5));

        JPanel acciones = new JPanel(new GridBagLayout());
        acciones.setBackground(BLANCO);
        acciones.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(229, 231, 235)),
                new EmptyBorder(12, 16, 12, 16)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 5, 4, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        c.gridx = 0; c.gridy = 0; acciones.add(label("Nuevo profesor"), c);
        c.gridx = 1; acciones.add(txtProfesor, c);
        c.gridx = 2; acciones.add(label("Salario base"), c);
        c.gridx = 3; acciones.add(spSalario, c);
        c.gridx = 4; c.weightx = 0;
        JButton agregar = boton("+ Agregar", NARANJA);
        acciones.add(agregar, c);

        c.gridx = 5;
        acciones.add(label("Asignar"), c);
        c.gridx = 6;
        acciones.add(cmbProfesor, c);
        c.gridx = 7;
        acciones.add(cmbCurso, c);
        c.gridx = 8;
        JButton asignar = boton("Asignar a curso", AZUL);
        acciones.add(asignar, c);

        agregar.addActionListener(e -> agregarProfesor());
        asignar.addActionListener(e -> asignarProfesor());

        panel.add(acciones, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaProfesores), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearBarraEstado() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(new Color(31, 41, 55));
        barra.setBorder(new EmptyBorder(8, 18, 8, 18));
        lblEstado.setForeground(new Color(229, 231, 235));
        lblEstado.setFont(new Font("SansSerif", Font.PLAIN, 12));
        barra.add(lblEstado, BorderLayout.WEST);
        return barra;
    }

    private void configurarEventos() {
        cmbEstado.addActionListener(e -> {
            if (controlador == null || cmbEstado.getSelectedIndex() == 0) return;
            EstadoMatricula estado = EstadoMatricula.valueOf((String) cmbEstado.getSelectedItem());
            mostrarEstudiantes(controlador.obtenerFilasPorEstado(estado));
        });
    }

    private void agregarEstudiante() {
        String carrera = (String) cmbNuevaCarrera.getSelectedItem();
        double promedio = ((Number) spPromedio.getValue()).doubleValue();
        if (controlador.agregarEstudiante(txtNombre.getText(), txtApellido.getText(), carrera, promedio)) {
            txtNombre.setText("");
            txtApellido.setText("");
            spPromedio.setValue(3.0);
            recargarCombos();
            mostrarEstudiantes(controlador.obtenerFilasEstudiantes());
            actualizarTotales();
        }
    }

    private void inscribir() {
        if (cmbEstudiante.getSelectedIndex() < 0 || cmbCurso.getSelectedIndex() < 0) return;
        int id = controlador.obtenerIdEstudiantePorIndice(cmbEstudiante.getSelectedIndex());
        String codigo = (String) cmbCurso.getSelectedItem();
        if (controlador.inscribirEstudiante(id, codigo)) {
            mostrarCursos();
            mostrarEstudiantes(controlador.obtenerFilasEstudiantes());
        }
    }

    private void retirarInscripcion() {
        if (cmbEstudiante.getSelectedIndex() < 0 || cmbCurso.getSelectedIndex() < 0) return;
        int id = controlador.obtenerIdEstudiantePorIndice(cmbEstudiante.getSelectedIndex());
        String codigo = (String) cmbCurso.getSelectedItem();
        if (controlador.retirarInscripcion(id, codigo)) {
            mostrarCursos();
            mostrarEstudiantes(controlador.obtenerFilasEstudiantes());
        }
    }

    private void agregarProfesor() {
        double salario = ((Number) spSalario.getValue()).doubleValue();
        if (controlador.agregarProfesor(txtProfesor.getText(), salario)) {
            txtProfesor.setText("");
            recargarCombos();
            mostrarProfesores();
            actualizarTotales();
        }
    }

    private void asignarProfesor() {
        if (cmbProfesor.getSelectedIndex() < 0 || cmbCurso.getSelectedIndex() < 0) return;
        int idProfesor = controlador.obtenerIdProfesorPorIndice(cmbProfesor.getSelectedIndex());
        String codigo = (String) cmbCurso.getSelectedItem();
        if (controlador.asignarProfesor(codigo, idProfesor)) {
            mostrarCursos();
            mostrarProfesores();
        }
    }

    private void recargarCombos() {
        cargarCombo(cmbCarrera, controlador.obtenerCarrerasUnicas(), "Todas las carreras");
        cargarCombo(cmbEstado, new String[]{"ACTIVO", "EGRESADO", "RETIRADO"}, "Todos los estados");
        cargarCombo(cmbNuevaCarrera, controlador.obtenerCarrerasUnicas(), "Seleccionar carrera");
        cargarCombo(cmbEstudiante, controlador.obtenerEstudiantesParaCombo(), null);
        cargarCombo(cmbCurso, controlador.obtenerCursos(), null);
        cargarCombo(cmbProfesor, controlador.obtenerProfesores(), null);
    }

    private void cargarCombo(JComboBox<String> combo, String[] datos, String primeraOpcion) {
        combo.removeAllItems();
        if (primeraOpcion != null) combo.addItem(primeraOpcion);
        for (String dato : datos) combo.addItem(dato);
    }

    public void setControlador(EstudianteController controlador) {
        this.controlador = controlador;
        recargarCombos();
        actualizarTotales();
        mostrarEstudiantes(controlador.obtenerFilasEstudiantes());
        mostrarCursos();
        mostrarProfesores();
    }

    public void mostrarEstudiante(Object[] fila) {
        limpiar(modeloEstudiantes);
        modeloEstudiantes.addRow(fila);
        lblEstado.setText("Se encontró 1 estudiante.");
    }

    public void mostrarEstudiantes(List<Object[]> filas) {
        limpiar(modeloEstudiantes);
        if (filas == null || filas.isEmpty()) {
            lblEstado.setText("No se encontraron estudiantes con ese criterio.");
            return;
        }
        for (Object[] fila : filas) modeloEstudiantes.addRow(fila);
        lblEstado.setText("Mostrando " + filas.size() + " estudiante(s).");
    }

    private void mostrarCursos() {
        limpiar(modeloCursos);
        for (Object[] fila : controlador.obtenerFilasCursos()) modeloCursos.addRow(fila);
    }

    private void mostrarProfesores() {
        limpiar(modeloProfesores);
        for (Object[] fila : controlador.obtenerFilasProfesores()) modeloProfesores.addRow(fila);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.WARNING_MESSAGE);
        lblEstado.setText(mensaje);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Operación realizada", JOptionPane.INFORMATION_MESSAGE);
        lblEstado.setText(mensaje);
    }

    private void actualizarTotales() {
        lblTotalEstudiantes.setText(String.valueOf(controlador.obtenerTotalEstudiantes()));
        lblTotalCursos.setText(String.valueOf(controlador.obtenerTotalCursos()));
        lblTotalProfesores.setText(String.valueOf(controlador.obtenerTotalProfesores()));
    }

    private DefaultTableModel crearModelo(String[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setForeground(TEXTO);
        tabla.setGridColor(new Color(229, 231, 235));
        tabla.setSelectionBackground(AZUL_CLARO);
        tabla.setSelectionForeground(TEXTO);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(243, 244, 246));
        tabla.getTableHeader().setForeground(TEXTO);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return tabla;
    }

    private JButton boton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setBorder(new EmptyBorder(9, 14, 9, 14));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JLabel label(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(SECUNDARIO);
        label.setFont(new Font("SansSerif", Font.BOLD, 11));
        return label;
    }

    private JLabel crearDato(String texto) {
        JLabel label = new JLabel(texto);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    private void limpiar(DefaultTableModel modelo) {
        modelo.setRowCount(0);
    }

    private void aplicarEstiloSistema() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Se conserva el estilo predeterminado si el sistema no permite cambiarlo.
        }
    }
}
