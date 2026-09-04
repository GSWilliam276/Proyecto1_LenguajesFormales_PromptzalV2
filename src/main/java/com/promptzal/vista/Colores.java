/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.promptzal.vista;

import java.awt.Color;
/**
 *
 * @author eduar
 */
public class Colores {
    //Fondo negro puro: ventana principal, paneles externos y areas no interactivas
    public static final Color FONDO_PRINCIPAL = new Color(0, 0, 0);        //#000000

    //Fondo del editor de texto y paneles internos (tokens, errores), gris muy oscuro
    //para distinguirse del negro puro del fondo principal sin perder el contraste
    public static final Color FONDO_PANEL = new Color(20, 20, 20);         //#141414

    //Color principal del tema (clay, el mismo tono naranja-terracota de Anthropic):
    //texto, iconos, y contenido de los botones
    public static final Color NARANJA = new Color(217, 119, 87);           //#D97757

    //Borde de botones y elementos interactivos - version mas oscura del clay
    //para dar jerarquia visual entre el texto/contenido y el marco que lo rodea
    public static final Color NARANJA_BORDE = new Color(153, 60, 29);      //#993C1D

    //Texto secundario: labels, subtitulos, texto de ayuda, menos protagonismo
    //que el texto principal pero todavia legible sobre el fondo negro
    public static final Color TEXTO_SECUNDARIO = new Color(217, 195, 163); //#D9C3A3

    //Reservado unicamente para indicar errores: texto en el panel de errores,
    //bordes de campos invalidos, mensajes de validacion
    public static final Color ERROR = new Color(226, 75, 74);              //#E24B4A
}
