/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.promptzal.controlador;

import com.promptzal.logica.AnalizadorLexico;
import com.promptzal.generadores.GeneradorReporte;
import com.promptzal.generadores.GeneradorAFD;
import com.promptzal.archivos.GestorArchivos;
import com.promptzal.excepciones.ExcepcionArchivoNoEncontrado;
import com.promptzal.excepciones.ExcepcionGraphvizNoDisponible;
import com.promptzal.modelo.Token;
import com.promptzal.modelo.ErrorLexico;
import java.util.List;
/**
 *
 * @author eduar
 */
public class ControladorAnalisis {
    private AnalizadorLexico analizador;
    private GeneradorReporte generadorReporte;
    private GeneradorAFD generadorAFD;
    private GestorArchivos gestorArchivos;

    public ControladorAnalisis() {
        this.generadorReporte = new GeneradorReporte();
        this.generadorAFD = new GeneradorAFD();
        this.gestorArchivos = new GestorArchivos();
    }

    public void analizarContenido(String contenido) {
        this.analizador = new AnalizadorLexico(contenido);
        this.analizador.analizar();
    }

    public List<Token> getTokens() {
        return analizador.getListaTokens();
    }

    public List<ErrorLexico> getErrores() {
        return analizador.getListaErrores();
    }

    public void generarReportes(String carpetaSalida) {
        generadorReporte.generarReporteTokens(getTokens(), carpetaSalida + "/reporte_tokens.html");
        generadorReporte.generarReporteErrores(getErrores(), carpetaSalida + "/reporte_errores.html");
        generadorReporte.generarReporteEstadisticas(getTokens(), getErrores(), carpetaSalida + "/reporte_estadisticas.html");
    }

    public void generarAFD(String carpetaSalida) throws ExcepcionGraphvizNoDisponible {
        generadorAFD.generarImagen(carpetaSalida + "/afd.dot", carpetaSalida + "/afd.png");
    }

    public String abrirArchivo(String ruta) throws ExcepcionArchivoNoEncontrado {
        return gestorArchivos.leerArchivo(ruta);
    }

    public boolean guardarArchivo(String ruta, String contenido) {
        return gestorArchivos.guardarArchivo(ruta, contenido);
    }
}
