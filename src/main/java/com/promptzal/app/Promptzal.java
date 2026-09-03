/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.promptzal.app;

import com.promptzal.generadores.GeneradorReporte;
import com.promptzal.logica.AnalizadorLexico;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import com.promptzal.vista.Colores;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author eduar
 */
public class Promptzal {

    public static void main(String[] args) {
        mostrarBienvenida();

        String ruta = seleccionarArchivo();
        if (ruta == null) {
            System.out.println(Colores.ROJO + "No se selecciono ningun archivo. Cerrando programa." + Colores.RESET);
            return;
        }

        try {
            String contenido = new String(Files.readAllBytes(java.nio.file.Paths.get(ruta)));
            AnalizadorLexico analizador = new AnalizadorLexico(contenido);
            analizador.analizar();
            analizador.mostrarTokensEnConsola();

            GeneradorReporte generador = new GeneradorReporte();
            generador.generarReporteTokens(analizador.getListaTokens(), "reporte_tokens.html");
            generador.generarReporteErrores(analizador.getListaErrores(), "reporte_errores.html");

            System.out.println(Colores.VERDE + "\nReportes HTML generados: reporte_tokens.html y reporte_errores.html" + Colores.RESET);

        } catch (IOException ex) {
            System.out.println(Colores.ROJO + "Error: no se pudo leer el archivo." + Colores.RESET);
        }
    }

        //Abre una ventana grafica para que el usuario seleccione el archivo .pz
        //con clics, en vez de tener que escribir la ruta completa a mano
        private static String seleccionarArchivo() {
            JFileChooser selector = new JFileChooser();
            selector.setDialogTitle("Selecciona un archivo .pz para analizar");
            selector.setFileFilter(new FileNameExtensionFilter("Archivos PromptZal (*.pz)", "pz"));

            int resultado = selector.showOpenDialog(null);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                return selector.getSelectedFile().getAbsolutePath();
            }
            return null;
    
        }
    
        private static void mostrarBienvenida(){
            System.out.println(Colores.NARANJA + Colores.NEGRITA);
            System.out.println("    _");
            System.out.println("  ~(o)>");
            System.out.println("  / ) \\        ===================");
            System.out.println(" ( (   )            PROMPTZAL");
            System.out.println("  \\ \\_/        ===================");          
            System.out.println("   ) )");
            System.out.println("  ( (");
            System.out.println("   ) )");
            System.out.println("  ( (");
            System.out.println("   \\ \\");
            System.out.println("    \\ \\");
            System.out.println("     `~'");
            System.out.println(Colores.RESET);
            System.out.println(Colores.AZUL + "Bienvenid@ a PromptZal - Analizador Lexico" + Colores.RESET);
        }            
}
