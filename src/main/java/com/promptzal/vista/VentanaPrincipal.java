/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.promptzal.vista;

import javax.swing.*;
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

    public static final String PANTALLA_BIENVENIDA = "bienvenida";
    public static final String PANTALLA_SELECCION = "seleccion";

    public VentanaPrincipal() {
        setTitle("PromptZal - Analizador Lexico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(Colores.FONDO_PRINCIPAL);

        panelContenedor.add(crearPanelBienvenida(), PANTALLA_BIENVENIDA);
        panelContenedor.add(crearPanelSeleccion(), PANTALLA_SELECCION);

        add(panelContenedor);
        cardLayout.show(panelContenedor, PANTALLA_BIENVENIDA);
    }

    private JPanel crearPanelBienvenida() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colores.FONDO_PRINCIPAL);

        //Espacio izquierdo para el quetzal, (se agregara despues)
        JPanel panelQuetzal = new JPanel();
        panelQuetzal.setBackground(Colores.FONDO_PRINCIPAL);
        panelQuetzal.setPreferredSize(new Dimension(350, 0));
        panel.add(panelQuetzal, BorderLayout.WEST);

        //Lado derecho: bienvenida
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
        botonIniciar.setFont(new Font("Consolas", Font.BOLD, 28));
        botonIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonIniciar.addActionListener(e -> cardLayout.show(panelContenedor, PANTALLA_SELECCION));

        panelDerecho.add(Box.createVerticalGlue());
        panelDerecho.add(titulo);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 14)));
        panelDerecho.add(subtitulo);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 40)));
        panelDerecho.add(botonIniciar);
        panelDerecho.add(Box.createVerticalGlue());

        panel.add(panelDerecho, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelSeleccion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colores.FONDO_PRINCIPAL);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel instruccion = new JLabel("Escribe tu programa .pz o selecciona un archivo:");
        instruccion.setFont(new Font("Consolas", Font.PLAIN, 20));
        instruccion.setForeground(Colores.TEXTO_SECUNDARIO);
        panel.add(instruccion, BorderLayout.NORTH);

        areaEditor = new JTextArea();
        areaEditor.setBackground(Colores.FONDO_PANEL);
        areaEditor.setForeground(Colores.NARANJA);
        areaEditor.setCaretColor(Colores.NARANJA);
        areaEditor.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(areaEditor);
        panel.add(scroll, BorderLayout.CENTER);

        JButton botonSeleccionar = new JButton("[ SELECCIONAR ARCHIVO .PZ ]");
        estilizarBoton(botonSeleccionar);
        botonSeleccionar.addActionListener(e -> seleccionarArchivo());

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Colores.FONDO_PRINCIPAL);
        panelInferior.add(botonSeleccionar);
        panel.add(panelInferior, BorderLayout.SOUTH);

        return panel;
    }

    private void seleccionarArchivo() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Selecciona un archivo .pz");
        selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos PromptZal (*.pz)", "pz"));
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            //Por ahora solo se muestra la ruta; la lectura real se conecta con GestorArchivos despues
            areaEditor.setText("Archivo seleccionado: " + archivo.getAbsolutePath());
        }
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Consolas", Font.BOLD, 20));
        boton.setForeground(Colores.NARANJA);
        boton.setBackground(Colores.FONDO_PANEL);
        boton.setBorder(BorderFactory.createLineBorder(Colores.NARANJA_BORDE, 2));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
