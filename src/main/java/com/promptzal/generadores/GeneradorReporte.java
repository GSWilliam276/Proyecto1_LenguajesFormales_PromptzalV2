/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.promptzal.generadores;

import com.promptzal.modelo.Token;
import com.promptzal.modelo.ErrorLexico;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author eduar
 */
public class GeneradorReporte {
    public boolean generarReporteTokens(List<Token> tokens, String rutaSalida) {
        StringBuilder html = new StringBuilder();
        html.append("<html>\n<head>\n");
        html.append("<style>\n");
        html.append("body { font-family: Arial, sans-serif; }\n");
        html.append("h1 { color: #2E4053; text-align: center; }\n");
        html.append("table { width: 90%; margin: auto; border-collapse: collapse; }\n");
        html.append("th { background-color: #AED6F1; padding: 8px; }\n");
        html.append("td { padding: 8px; border-bottom: 1px solid #ddd; text-align: center; }\n");
        html.append("</style>\n</head>\n<body>\n");
        html.append("<h1>Reporte de Tokens - PromptZal</h1>\n");
        html.append("<table>\n");
        html.append("<tr><th>No.</th><th>Lexema</th><th>Tipo</th><th>Fila</th><th>Columna</th></tr>\n");

        for (Token t : tokens) {
            html.append("<tr>");
            html.append("<td>").append(t.getNumero()).append("</td>");
            html.append("<td>").append(t.getLexema()).append("</td>");
            html.append("<td>").append(t.getTipo()).append("</td>");
            html.append("<td>").append(t.getFila()).append("</td>");
            html.append("<td>").append(t.getColumna()).append("</td>");
            html.append("</tr>\n");
        }

        html.append("</table>\n</body>\n</html>");

        return escribirArchivo(rutaSalida, html.toString());
    }

    public boolean generarReporteErrores(List<ErrorLexico> errores, String rutaSalida) {
        StringBuilder html = new StringBuilder();
        html.append("<html>\n<head>\n");
        html.append("<style>\n");
        html.append("body { font-family: Arial, sans-serif; }\n");
        html.append("h1 { color: #922B21; text-align: center; }\n");
        html.append("table { width: 90%; margin: auto; border-collapse: collapse; }\n");
        html.append("th { background-color: #F5B7B1; padding: 8px; }\n");
        html.append("td { padding: 8px; border-bottom: 1px solid #ddd; text-align: center; }\n");
        html.append("p { text-align: center; font-size: 18px; color: green; }\n");
        html.append("</style>\n</head>\n<body>\n");
        html.append("<h1>Reporte de Errores Lexicos - PromptZal</h1>\n");

        if (errores.isEmpty()) {
            html.append("<p>No se encontraron errores lexicos.</p>\n");
        } else {
            html.append("<table>\n");
            html.append("<tr><th>Lexema</th><th>Descripcion</th><th>Fila</th><th>Columna</th></tr>\n");
            for (ErrorLexico e : errores) {
                html.append("<tr>");
                html.append("<td>").append(e.getLexema()).append("</td>");
                html.append("<td>").append(e.getDescripcion()).append("</td>");
                html.append("<td>").append(e.getFila()).append("</td>");
                html.append("<td>").append(e.getColumna()).append("</td>");
                html.append("</tr>\n");
            }
            html.append("</table>\n");
        }

        html.append("</body>\n</html>");

        return escribirArchivo(rutaSalida, html.toString());
    }

    private boolean escribirArchivo(String ruta, String contenido) {
        try (FileWriter writer = new FileWriter(ruta)) {
            writer.write(contenido);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private static final String[] TIPOS_TOKEN = {
        "IDENTIFICADOR", "PALABRA_RESERVADA", "COMANDO_IA", "CONECTOR",
        "DIRECTIVA", "CADENA", "ENTERO", "DECIMAL", "OPERADOR", "DELIMITADOR"
    };

    public boolean generarReporteEstadisticas(List<Token> tokens, List<ErrorLexico> errores, String rutaSalida) {
        int totalTokens = tokens.size();
        int totalErrores = errores.size();

        int totalLineas = 0;
        for (Token t : tokens) {
            if (t.getFila() > totalLineas) {
            totalLineas = t.getFila();
            }
        }

        StringBuilder html = new StringBuilder();
        html.append("<html>\n<head>\n");
        html.append("<style>\n");
        html.append("body { font-family: Arial, sans-serif; }\n");
        html.append("h1 { color: #1A5276; text-align: center; }\n");
        html.append("table { width: 60%; margin: auto; border-collapse: collapse; }\n");
        html.append("th { background-color: #A9DFBF; padding: 8px; }\n");
        html.append("td { padding: 8px; border-bottom: 1px solid #ddd; text-align: center; }\n");
        html.append("</style>\n</head>\n<body>\n");
        html.append("<h1>Reporte de Estadisticas - PromptZal</h1>\n");

        html.append("<table>\n");
        html.append("<tr><th>Metrica</th><th>Valor</th></tr>\n");
        html.append("<tr><td>Total de tokens</td><td>").append(totalTokens).append("</td></tr>\n");
        html.append("<tr><td>Total de lineas</td><td>").append(totalLineas).append("</td></tr>\n");
        html.append("<tr><td>Total de errores</td><td>").append(totalErrores).append("</td></tr>\n");
        html.append("</table>\n<br>\n");

        html.append("<table>\n");
        html.append("<tr><th>Tipo de token</th><th>Frecuencia</th></tr>\n");
        for (String tipo : TIPOS_TOKEN) {
            int contador = 0;
            for (Token t : tokens) {
                if (t.getTipo().equals(tipo)) {
                    contador++;
                }
            }
        html.append("<tr><td>").append(tipo).append("</td><td>").append(contador).append("</td></tr>\n");
        }
        html.append("</table>\n");

        html.append("</body>\n</html>");
        return escribirArchivo(rutaSalida, html.toString());
    }
}
