/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.promptzal.logica;

import com.promptzal.modelo.Token;
import com.promptzal.modelo.ErrorLexico;
import java.util.ArrayList;
import java.util.List;
import com.promptzal.vista.Colores;
/**
 *
 * @author eduar
 */
public class AnalizadorLexico {
    //Atributos
    private String texto;
    private int posicion;
    private int fila;
    private int columna;
    private int contadorTokens;

    private List<Token> listaTokens;
    private List<ErrorLexico> listaErrores;

    //Listas de palabras conocidas, para clasificar contra ellas
    private static final String[] PALABRAS_RESERVADAS = {"AGENTE", "contexto", "variable", "EJECUTAR", "EXPORTAR"};
    private static final String[] COMANDOS_IA = {"PREGUNTAR", "GENERAR", "RESUMIR", "ANALIZAR", "TRADUCIR", "CLASIFICAR", "EXTRAER", "CARGAR"};
    private static final String[] CONECTORES_PALABRA = {"SOBRE", "DESDE", "EN", "COMO"};

    //Constructor
    public AnalizadorLexico(String texto) {
        this.texto = texto;
        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.contadorTokens = 0;
        this.listaTokens = new ArrayList<>();
        this.listaErrores = new ArrayList<>();
    }
    
    private char espiar() {
        //Mira el caracter en la posicion siguiente
        if (posicion + 1 < texto.length()) {
            return texto.charAt(posicion + 1);
        }
        return '\0'; //Caracter nulo, indica que no hay siguiente caracter
    }
    
    private void avanzar() {
        //Mueve el puntero una posicion hacia adelante, actualiza fila/columna 
        //correctamente segun lo que se acaba de dejar atras
        if (texto.charAt(posicion) == '\n') {
            fila++;
            columna = 1;
        } else {
            columna++;
        }
        posicion++;
    }
    
    private void leerPalabra() {
        int filaInicio = fila;
        int columnaInicio = columna;
        StringBuilder palabra = new StringBuilder();

        //Mientras el caracter actual sea letra, digito o guion bajo, se sigue acumulando
        while (posicion < texto.length() && esCaracterDePalabra(texto.charAt(posicion))) {
            palabra.append(texto.charAt(posicion));
            avanzar();
        }

        String lexema = palabra.toString();
        String tipo = clasificarPalabra(lexema);

        contadorTokens++;
        Token token = new Token(contadorTokens, lexema, tipo, filaInicio, columnaInicio);
        listaTokens.add(token);
    }

    //Determina si un caracter puede formar parte de una palabra (identificador,
    //palabra reservada, comando o conector)
    private boolean esCaracterDePalabra(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    private String clasificarPalabra(String palabra) {
        //Respeta el orden de prioridad
        if (contiene(PALABRAS_RESERVADAS, palabra)) {
            return "PALABRA_RESERVADA";
        }
        if (contiene(COMANDOS_IA, palabra)) {
            return "COMANDO_IA";
        }
        if (contiene(CONECTORES_PALABRA, palabra)) {
            return "CONECTOR";
        }
        return "IDENTIFICADOR";
    }

    private boolean contiene(String[] arreglo, String valor) {
        //Recorre el arreglo comparando uno por uno
        for (String s : arreglo) {
            if (s.equals(valor)) {
                return true;
            }
        }
        return false;
    }
    
    private void leerCadena() {
        int filaInicio = fila;
        int columnaInicio = columna;
        StringBuilder cadena = new StringBuilder();

        avanzar(); //Se salta la comilla de apertura, no se guarda como parte del contenido

        boolean cerrada = false;
        while (posicion < texto.length()) {
            char actual = texto.charAt(posicion);
        
            if (actual == '"') {
                avanzar(); //Se salta la comilla de cierre tambien
                cerrada = true;
                break;
            }
            if (actual == '\n') {
                //La cadena no puede cruzar un salto de linea sin cerrarse
                break;
            }
        
            cadena.append(actual);
            avanzar();
        }

        if (cerrada) {
            contadorTokens++;
            Token token = new Token(contadorTokens, cadena.toString(), "CADENA", filaInicio, columnaInicio);
            listaTokens.add(token);
        } else {
            ErrorLexico error = new ErrorLexico(cadena.toString(), "Cadena sin cerrar", filaInicio, columnaInicio);
            listaErrores.add(error);
        }
    }
    
    private void leerNumero() {
        int filaInicio = fila;
        int columnaInicio = columna;
        StringBuilder numero = new StringBuilder();
        boolean esDecimal = false;

        while (posicion < texto.length()) {
            char actual = texto.charAt(posicion);

            if (Character.isDigit(actual)) {
                numero.append(actual);
                avanzar();
            } else if (actual == '.' && !esDecimal && Character.isDigit(espiar())) {
                //Solo se acepta el punto si aun no hay otro punto y si despues viene un digito
                esDecimal = true;
                numero.append(actual);
                avanzar();
            } else {
                break;
            }
        }

        String lexema = numero.toString();
        String tipo = esDecimal ? "DECIMAL" : "ENTERO";

        contadorTokens++;
        Token token = new Token(contadorTokens, lexema, tipo, filaInicio, columnaInicio);
        listaTokens.add(token);
    }
    
    private void leerComentario() {
        int filaInicio = fila;
        int columnaInicio = columna;
    
        avanzar(); //Se salta la primera barra "/"
        char siguiente = texto.charAt(posicion);

        if (siguiente == '/') {
            //Comentario de linea: se salta todo hasta encontrar un salto de linea o el fin del archivo
            avanzar(); // se salta la segunda barra
            while (posicion < texto.length() && texto.charAt(posicion) != '\n') {
                avanzar();
            }
        } else if (siguiente == '*') {
            //Comentario de bloque: se salta todo hasta encontrar */
            avanzar(); //Se salta el asterisco
            boolean cerrado = false;
            while (posicion < texto.length()) {
                if (texto.charAt(posicion) == '*' && espiar() == '/') {
                    avanzar(); //salta el *
                    avanzar(); //salta el /
                    cerrado = true;
                    break;
                }
                avanzar();
            }
            if (!cerrado) {
                ErrorLexico error = new ErrorLexico("/*", "Comentario de bloque sin cerrar", filaInicio, columnaInicio);
                listaErrores.add(error);
            }
        }
    }
    
    private void leerDirectiva() {
        int filaInicio = fila;
        int columnaInicio = columna;
        StringBuilder directiva = new StringBuilder();
    
        avanzar(); //Se salta el simbolo @, no se guarda como parte del contenido
    
        while (posicion < texto.length() && esCaracterDePalabra(texto.charAt(posicion))) {
            directiva.append(texto.charAt(posicion));
            avanzar();
        }
    
        String lexema = directiva.toString();
    
        //Si no es una directiva valida (incluyendo el caso de quedar vacia),
        //se reporta como error lexico en vez de generar un token
        if (!esDirectivaValida(lexema)) {
            ErrorLexico error = new ErrorLexico("@" + lexema, "Directiva no reconocida", filaInicio, columnaInicio);
            listaErrores.add(error);
            return;
        }
    
        contadorTokens++;
        Token token = new Token(contadorTokens, lexema, "DIRECTIVA", filaInicio, columnaInicio);
        listaTokens.add(token);
    }

    private boolean esDirectivaValida(String directiva) {
        String[] directivasValidas = {"modelo", "rol", "formato"};
        return contiene(directivasValidas, directiva);
    }
    
    public void analizar() {
        while (posicion < texto.length()) {
            char actual = texto.charAt(posicion);

            if (actual == ' ' || actual == '\t' || actual == '\r' || actual == '\n') {
                //Espacios en blanco y saltos de linea se ignoran, solo se avanza
                avanzar();
            } else if (Character.isLetter(actual) || actual == '_') {
                leerPalabra();
            } else if (actual == '@') {
                leerDirectiva();
            } else if (actual == '"') {
                leerCadena();
            } else if (Character.isDigit(actual)) {
                leerNumero();
            } else if (actual == '/') {
                leerComentario();
            } else if (actual == '-') {
                leerConectorFlecha();
            } else if (actual == '=' || actual == '+' || actual == '{' || actual == '}' || actual == '(' || actual == ')' || actual == ',') {
                leerSimboloSuelto(actual);
            } else {
                //No encaja en ninguna categoria valida: caracter no reconocido
                int filaError = fila;
                int columnaError = columna;
                ErrorLexico error = new ErrorLexico(String.valueOf(actual), "Caracter no reconocido", filaError, columnaError);
                listaErrores.add(error);
                avanzar();
            }
        }
    }
    
    private void leerConectorFlecha() {
        //Maneja el caso de -, que puede ser -> o error
        int filaInicio = fila;
        int columnaInicio = columna;

        if (espiar() == '>') {
            avanzar(); // salta el -
            avanzar(); // salta el >
            contadorTokens++;
            Token token = new Token(contadorTokens, "->", "CONECTOR", filaInicio, columnaInicio);
            listaTokens.add(token);
        } else {
            ErrorLexico error = new ErrorLexico("-", "Caracter no reconocido", filaInicio, columnaInicio);
            listaErrores.add(error);
            avanzar();
        }
    }
    
    private void leerSimboloSuelto(char simbolo) {
        //Maneja los simbolos de un solo caracter
        int filaInicio = fila;
        int columnaInicio = columna;
        String tipo = (simbolo == '=' || simbolo == '+') ? "OPERADOR" : "DELIMITADOR";

        avanzar();
        contadorTokens++;
        Token token = new Token(contadorTokens, String.valueOf(simbolo), tipo, filaInicio, columnaInicio);
        listaTokens.add(token);
    }
    
    public void mostrarTokensEnConsola() {
        System.out.println(Colores.NARANJA + "=== TOKENS RECONOCIDOS ===");
        System.out.printf("%-5s %-20s %-20s %-6s %-8s%n", "No.", "Lexema", "Tipo", "Fila", "Columna");
        for (Token t : listaTokens) {
            System.out.printf("%-5d %-20s %-20s %-6d %-8d %n",
                t.getNumero(), t.getLexema(), t.getTipo(), t.getFila(), t.getColumna());
        }

        System.out.println( "\n=== ERRORES LEXICOS ===" );
        if (listaErrores.isEmpty()) {
            System.out.println( "No se encontraron errores lexicos." );
        } else {
            System.out.printf("%-15s %-30s %-6s %-8s%n", "Lexema", "Descripcion", "Fila", "Columna");
            for (ErrorLexico e : listaErrores) {
                System.out.printf( "%-15s %-30s %-6d %-8d %n",
                    e.getLexema(), e.getDescripcion(), e.getFila(), e.getColumna());
            }
        }
    }
    
    //Getters
    public List<Token> getListaTokens() {
        return listaTokens;
    }

    public List<ErrorLexico> getListaErrores() {
        return listaErrores;
    }
}
