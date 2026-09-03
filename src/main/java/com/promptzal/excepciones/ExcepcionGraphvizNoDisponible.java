/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.promptzal.excepciones;

/**
 *
 * @author eduar
 */
public class ExcepcionGraphvizNoDisponible extends Exception {
    public ExcepcionGraphvizNoDisponible() {
        super("No se encontro Graphviz instalado o no esta disponible en el PATH del sistema.");
    }
}
