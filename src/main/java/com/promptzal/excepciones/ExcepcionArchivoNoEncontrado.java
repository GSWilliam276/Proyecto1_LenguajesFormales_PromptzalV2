/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.promptzal.excepciones;

/**
 *
 * @author eduar
 */
public class ExcepcionArchivoNoEncontrado extends Exception {
     public ExcepcionArchivoNoEncontrado(String ruta) {
        super("No se pudo encontrar o leer el archivo: " + ruta);
    }
}
