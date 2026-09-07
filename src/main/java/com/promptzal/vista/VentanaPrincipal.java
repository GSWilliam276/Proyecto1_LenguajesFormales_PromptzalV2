/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.promptzal.vista;

import com.promptzal.controlador.ControladorAnalisis;
import com.promptzal.excepciones.ExcepcionArchivoNoEncontrado;
import com.promptzal.excepciones.ExcepcionGraphvizNoDisponible;
import com.promptzal.modelo.Token;
import com.promptzal.modelo.ErrorLexico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
/**
 *
 * @author eduar
 */
public class VentanaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelContenedor;
    private JTextArea areaEditor;
    private DefaultTableModel modeloTokens;
    private DefaultTableModel modeloErrores;

    private final ControladorAnalisis controlador = new ControladorAnalisis();

    public static final String PANTALLA_BIENVENIDA = "bienvenida";
    public static final String PANTALLA_SELECCION = "seleccion";
    public static final String PANTALLA_RESULTADOS = "resultados";

    public VentanaPrincipal() {
        setTitle("PromptZal - Analizador Léxico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(Colores.FONDO_PRINCIPAL);

        panelContenedor.add(crearPanelBienvenida(), PANTALLA_BIENVENIDA);
        panelContenedor.add(crearPanelSeleccion(), PANTALLA_SELECCION);
        panelContenedor.add(crearPanelResultados(), PANTALLA_RESULTADOS);

        add(panelContenedor);
        cardLayout.show(panelContenedor, PANTALLA_BIENVENIDA);
    }

    private JPanel crearPanelBienvenida() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colores.FONDO_PRINCIPAL);

        JPanel panelQuetzal = new JPanel(new BorderLayout());
        panelQuetzal.setBackground(Colores.FONDO_PRINCIPAL);
        panelQuetzal.setPreferredSize(new Dimension(700, 0));

        ImageIcon iconoOriginal = new ImageIcon("src/main/resources/quetzal.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(690, -1, Image.SCALE_SMOOTH);
        JLabel labelQuetzal = new JLabel(new ImageIcon(imagenEscalada));
        labelQuetzal.setHorizontalAlignment(SwingConstants.CENTER);
        labelQuetzal.setVerticalAlignment(SwingConstants.CENTER);
        panelQuetzal.add(labelQuetzal, BorderLayout.CENTER);

        panel.add(panelQuetzal, BorderLayout.WEST);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBackground(Colores.FONDO_PRINCIPAL);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));

        JLabel titulo = new JLabel("[ PROMPTZAL ]");
        titulo.setFont(new Font("Consolas", Font.BOLD, 50));
        titulo.setForeground(Colores.NARANJA);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("-- Analizador Léxico --");
        subtitulo.setFont(new Font("Consolas", Font.PLAIN, 30));
        subtitulo.setForeground(Colores.NARANJA);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonIniciar = new JButton("[ INICIAR SISTEMA ]");
        estilizarBoton(botonIniciar);
        botonIniciar.setFont(new Font("Consolas", Font.BOLD, 25));
        botonIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonIniciar.addActionListener(e -> cardLayout.show(panelContenedor, PANTALLA_SELECCION));
        
        JButton botonSalir = new JButton("[ SALIR ]");
        botonSalir.setFont(new Font("Consolas", Font.BOLD, 20));
        botonSalir.setForeground(Colores.ERROR);
        botonSalir.setBackground(Colores.FONDO_PANEL);
        botonSalir.setBorder(BorderFactory.createLineBorder(Colores.ERROR, 2));
        botonSalir.setFocusPainted(false);
        botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.addActionListener(e -> System.exit(0));

        panelDerecho.add(Box.createVerticalGlue());
        panelDerecho.add(titulo);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 14)));
        panelDerecho.add(subtitulo);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 40)));
        panelDerecho.add(botonIniciar);
        panelDerecho.add(Box.createVerticalGlue());
        panelDerecho.add(botonIniciar);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 16)));
        panelDerecho.add(botonSalir);
        panelDerecho.add(Box.createVerticalGlue());

        panel.add(panelDerecho, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelSeleccion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colores.FONDO_PRINCIPAL);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel instruccion = new JLabel("Escribe tu programa .pz o selecciona un archivo:");
        instruccion.setFont(new Font("Consolas", Font.PLAIN, 14));
        instruccion.setForeground(Colores.NARANJA);
        panel.add(instruccion, BorderLayout.NORTH);

        areaEditor = new JTextArea();
        areaEditor.setBackground(Colores.FONDO_PANEL);
        areaEditor.setForeground(Colores.NARANJA);
        areaEditor.setCaretColor(Colores.NARANJA);
        areaEditor.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(areaEditor);
        panel.add(scroll, BorderLayout.CENTER);

        JButton botonRegresar = new JButton("[ REGRESAR ]");
        estilizarBoton(botonRegresar);
        botonRegresar.addActionListener(e -> cardLayout.show(panelContenedor, PANTALLA_BIENVENIDA));

        JButton botonSeleccionar = new JButton("[ ABRIR ARCHIVO .PZ ]");
        estilizarBoton(botonSeleccionar);
        botonSeleccionar.addActionListener(e -> seleccionarArchivo());

        JButton botonGuardar = new JButton("[ GUARDAR ]");
        estilizarBoton(botonGuardar);
        botonGuardar.addActionListener(e -> guardarArchivo());

        JButton botonAnalizar = new JButton("[ ANALIZAR ]");
        estilizarBoton(botonAnalizar);
        botonAnalizar.addActionListener(e -> analizarContenido());

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Colores.FONDO_PRINCIPAL);
        panelInferior.add(botonRegresar);
        panelInferior.add(botonSeleccionar);
        panelInferior.add(botonGuardar);
        panelInferior.add(botonAnalizar);
        panel.add(panelInferior, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colores.FONDO_PRINCIPAL);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        modeloTokens = new DefaultTableModel(new String[]{"No.", "Lexema", "Tipo", "Fila", "Columna"}, 0);
        JTable tablaTokens = new JTable(modeloTokens);
        estilizarTabla(tablaTokens);

        modeloErrores = new DefaultTableModel(new String[]{"Lexema", "Descripción", "Fila", "Columna"}, 0);
        JTable tablaErrores = new JTable(modeloErrores);
        estilizarTabla(tablaErrores);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                crearPanelConTitulo("Tokens", tablaTokens),
                crearPanelConTitulo("Errores", tablaErrores));
        splitPane.setResizeWeight(0.6);
        splitPane.setBackground(Colores.FONDO_PRINCIPAL);
        panel.add(splitPane, BorderLayout.CENTER);

        JButton botonReportes = new JButton("[ GENERAR REPORTES HTML ]");
        estilizarBoton(botonReportes);
        botonReportes.addActionListener(e -> generarReportes());

        JButton botonAFD = new JButton("[ GENERAR IMAGEN AFD ]");
        estilizarBoton(botonAFD);
        botonAFD.addActionListener(e -> generarAFD());

        JButton botonVolver = new JButton("[ VOLVER AL EDITOR ]");
        estilizarBoton(botonVolver);
        botonVolver.addActionListener(e -> cardLayout.show(panelContenedor, PANTALLA_SELECCION));

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Colores.FONDO_PRINCIPAL);
        panelInferior.add(botonVolver);
        panelInferior.add(botonReportes);
        panelInferior.add(botonAFD);
        panel.add(panelInferior, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelConTitulo(String titulo, JTable tabla) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colores.FONDO_PRINCIPAL);
        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Consolas", Font.BOLD, 14));
        label.setForeground(Colores.NARANJA);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(label, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Colores.FONDO_PANEL);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setBackground(Colores.FONDO_PANEL);
        tabla.setForeground(Colores.NARANJA);
        tabla.setGridColor(Colores.FONDO_PRINCIPAL);
        tabla.getTableHeader().setBackground(Colores.NARANJA);
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 13));
        tabla.setFont(new Font("Consolas", Font.PLAIN, 13));
        tabla.setRowHeight(22);
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Consolas", Font.BOLD, 14));
        boton.setForeground(Colores.NARANJA);
        boton.setBackground(Colores.FONDO_PANEL);
        boton.setBorder(BorderFactory.createLineBorder(Colores.NARANJA_BORDE, 2));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void seleccionarArchivo() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Selecciona un archivo .pz");
        selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos PromptZal (*.pz)", "pz"));
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = selector.getSelectedFile().getAbsolutePath();
            try {
                String contenido = controlador.abrirArchivo(ruta);
                areaEditor.setText(contenido);
            } catch (ExcepcionArchivoNoEncontrado ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarArchivo() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar archivo .pz");
        int resultado = selector.showSaveDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            String ruta = archivo.getAbsolutePath();
            if (!ruta.endsWith(".pz")) {
                ruta += ".pz";
            }
            boolean exito = controlador.guardarArchivo(ruta, areaEditor.getText());
            if (exito) {
                JOptionPane.showMessageDialog(this, "Archivo guardado correctamente en:\n" + ruta, "Guardado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void analizarContenido() {
        controlador.analizarContenido(areaEditor.getText());

        modeloTokens.setRowCount(0);
        for (Token t : controlador.getTokens()) {
            modeloTokens.addRow(new Object[]{t.getNumero(), t.getLexema(), t.getTipo(), t.getFila(), t.getColumna()});
        }

        modeloErrores.setRowCount(0);
        for (ErrorLexico e : controlador.getErrores()) {
            modeloErrores.addRow(new Object[]{e.getLexema(), e.getDescripcion(), e.getFila(), e.getColumna()});
        }

        cardLayout.show(panelContenedor, PANTALLA_RESULTADOS);
    }

    private void generarReportes() {
        controlador.generarReportes("recursos/reportes");
        JOptionPane.showMessageDialog(this, "Reportes generados en recursos/Reportes/", "Listo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void generarAFD() {
        try {
            controlador.generarAFD("recursos/afd");
            JOptionPane.showMessageDialog(this, "Imagen del AFD generada en recursos/AFD/", "Listo", JOptionPane.INFORMATION_MESSAGE);
        } catch (ExcepcionGraphvizNoDisponible ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
