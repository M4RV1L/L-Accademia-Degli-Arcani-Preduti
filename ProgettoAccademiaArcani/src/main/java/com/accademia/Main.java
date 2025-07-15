package com.accademia;

import com.mycompany.view.StartScreen;
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author marcovillano
 */
public class Main {
    public static void main(String[] args) {
        // Assicura che l'interfaccia utente venga creata e gestita sul Thread di dispatch degli eventi di Swing.
        SwingUtilities.invokeLater(() -> {
            new StartScreen(); // Avvia la schermata iniziale del gioco
        });
    }
}