/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyecto;

/**
 *
 * @author David
 */
public class Main {
    public static void main(String[] args) {
        // Crear una instancia de GestionProductosGUI en el hilo de despacho de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            new GestionProductosGUI();
        });
    }
}

