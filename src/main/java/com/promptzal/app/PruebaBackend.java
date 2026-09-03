/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.promptzal.app;

import com.promptzal.controlador.ControladorAnalisis;
import com.promptzal.excepciones.ExcepcionArchivoNoEncontrado;
import com.promptzal.excepciones.ExcepcionGraphvizNoDisponible;
/**
 *
 * @author eduar
 */
public class PruebaBackend {
    public static void main(String[] args) {
        ControladorAnalisis controlador = new ControladorAnalisis();

        try {
            String contenido = controlador.abrirArchivo("recursos/Casos de Prueba/caso5.pz");

            controlador.analizarContenido(contenido);

            System.out.println("Tokens encontrados: " + controlador.getTokens().size());
            System.out.println("Errores encontrados: " + controlador.getErrores().size());

            for (var t : controlador.getTokens()) {
                System.out.println(t.getNumero() + " | " + t.getLexema() + " | " + t.getTipo()
                    + " | fila " + t.getFila() + " | col " + t.getColumna());
            }

            System.out.println("\n--- ERRORES ---");
            for (var e : controlador.getErrores()) {
                System.out.println(e.getLexema() + " | " + e.getDescripcion()
                    + " | fila " + e.getFila() + " | col " + e.getColumna());
            }

            controlador.generarReportes("recursos/Casos de Prueba");
            System.out.println("\nReportes generados en recursos/recursos/");

            controlador.generarAFD("recursos/AFD");
            System.out.println("Imagen del AFD regenerada en recursos/AFD/");

        } catch (ExcepcionArchivoNoEncontrado ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (ExcepcionGraphvizNoDisponible ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
