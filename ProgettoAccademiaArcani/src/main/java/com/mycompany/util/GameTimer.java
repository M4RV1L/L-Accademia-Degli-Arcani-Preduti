/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import com.mycompany.controller.GameController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

/**
 *
 * @author marcovillano
 */
public class GameTimer {
    private Timer timer;
    private long startTime; // Tempo di inizio del gioco o del caricamento
    private GameController gameController;

    public GameTimer(GameController gameController) {
        this.gameController = gameController;
        this.startTime = System.currentTimeMillis(); // Inizia il timer al momento della creazione
        initializeTimer();
    }

    private void initializeTimer() {
        timer = new Timer(1000, new ActionListener() { // Aggiorna ogni secondo
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
                gameController.updateTime(elapsedSeconds);
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    // Per caricare un gioco salvato, reimposta il tempo di inizio e riprende il timer
    public void resumeTimer(long savedStartTime) {
        this.startTime = savedStartTime;
        if (!timer.isRunning()) {
            timer.start();
        }
    }
}