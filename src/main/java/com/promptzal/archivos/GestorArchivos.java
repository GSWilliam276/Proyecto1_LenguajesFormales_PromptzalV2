/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.promptzal.archivos;

import com.promptzal.excepciones.ExcepcionArchivoNoEncontrado;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 *
 * @author eduar
 */
public class GestorArchivos {
    public String leerArchivo(String ruta) throws ExcepcionArchivoNoEncontrado {
        try {
            return new String(Files.readAllBytes(Paths.get(ruta)));
        } catch (IOException ex) {
            throw new ExcepcionArchivoNoEncontrado(ruta);
        }
    }

    public boolean guardarArchivo(String ruta, String contenido) {
        try (FileWriter writer = new FileWriter(ruta)) {
            writer.write(contenido);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
