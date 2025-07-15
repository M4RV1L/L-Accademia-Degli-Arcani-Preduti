/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.view;

import com.mycompany.controller.GameController;
import com.mycompany.progettoaccademiaarcani.model.Item;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author marcovillano
 */
public class GameGUI extends JFrame {
    private JTextArea outputArea;
    private JTextField inputField;
    private JLabel currentRoomLabel;
    private JLabel inventoryLabel;
    private JLabel sealsLabel;
    private JLabel timerLabel;
    private GameController gameController;
    private String playerName;

    // Colori consigliati: puoi cambiarli!
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 40); // Grigio-blu scuro, per un'atmosfera misteriosa
    private static final Color TEXT_COLOR = new Color(200, 200, 200); // Grigio chiaro per il testo
    private static final Color BORDER_COLOR = new Color(70, 70, 80); // Grigio leggermente più chiaro per i bordi

    public GameGUI(String playerName) {
        this.playerName = playerName;
        setTitle("L'Accademia degli Arcani Perduti");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));
        // Imposta il colore di sfondo del JFrame stesso, se visibile
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Pannello superiore per le informazioni di stato
        JPanel infoPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        infoPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        infoPanel.setBackground(BACKGROUND_COLOR); // Imposta il colore di sfondo del pannello
        
        currentRoomLabel = new JLabel("Stanza Attuale: ");
        inventoryLabel = new JLabel("Inventario: ");
        sealsLabel = new JLabel("Sigilli: ");
        timerLabel = new JLabel("Tempo: ");

        // Imposta il colore del testo per le label
        currentRoomLabel.setForeground(TEXT_COLOR);
        inventoryLabel.setForeground(TEXT_COLOR);
        sealsLabel.setForeground(TEXT_COLOR);
        timerLabel.setForeground(TEXT_COLOR);

        infoPanel.add(currentRoomLabel);
        infoPanel.add(inventoryLabel);
        infoPanel.add(sealsLabel);
        infoPanel.add(timerLabel);
        add(infoPanel, BorderLayout.NORTH);

        // Area di output del gioco
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBackground(BACKGROUND_COLOR.brighter()); // Un colore leggermente più chiaro per l'area di testo
        outputArea.setForeground(TEXT_COLOR); // Colore del testo dell'output
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1)); // Bordo del scroll pane
        add(scrollPane, BorderLayout.CENTER);

        // Pannello inferiore per l'input e i pulsanti
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR); // Imposta il colore di sfondo del pannello

        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputField.setBackground(BACKGROUND_COLOR.brighter()); // Colore input field
        inputField.setForeground(TEXT_COLOR); // Colore testo input
        inputField.setCaretColor(TEXT_COLOR); // Colore del cursore
        
        inputField.addActionListener(e -> {
            String command = inputField.getText();
            inputField.setText("");
            gameController.processCommand(command);
        });
        inputPanel.add(inputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR); // Colore di sfondo dei bottoni
        
        JButton saveButton = new JButton("Salva");
        JButton loadButton = new JButton("Carica");

        // Stile per i bottoni
        styleButton(saveButton);
        styleButton(loadButton);

        saveButton.addActionListener(e -> gameController.saveGame());
        loadButton.addActionListener(e -> gameController.loadGame());

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        inputPanel.add(buttonPanel, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        this.gameController = new GameController(this);

        setVisible(true);
    }

    // Metodo helper per stilizzare i bottoni
    private void styleButton(JButton button) {
        button.setBackground(BORDER_COLOR.darker()); // Un colore più scuro per il bottone
        button.setForeground(TEXT_COLOR); // Testo chiaro
        button.setFocusPainted(false); // Rimuove il bordo di focus
        button.setFont(new Font("Arial", Font.BOLD, 12));
    }

    public String getPlayerName() {
        return playerName;
    }

    public void displayMessage(String message) {
        outputArea.append(message + "\n"); // Rimuovi il doppio a capo per una migliore leggibilità
        outputArea.setCaretPosition(outputArea.getDocument().getLength()); // Scorri automaticamente
    }

    public void updateCurrentRoom(String roomName) {
        currentRoomLabel.setText("Stanza Attuale: " + roomName);
    }

    public void updateInventoryDisplay(List<Item> inventory) {
        StringBuilder sb = new StringBuilder("Inventario: ");
        if (inventory.isEmpty()) {
            sb.append("Vuoto");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                sb.append(inventory.get(i).getName());
                if (i < inventory.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        inventoryLabel.setText(sb.toString());
    }

    public void updateSealsDisplay(int seals) {
        sealsLabel.setText("Sigilli: " + seals + "/4");
    }

    public void updateTimerDisplay(long elapsedSeconds) {
        long minutes = TimeUnit.SECONDS.toMinutes(elapsedSeconds);
        long seconds = elapsedSeconds - TimeUnit.MINUTES.toSeconds(minutes);
        timerLabel.setText(String.format("Tempo: %02d:%02d", minutes, seconds));
    }

    public GameController getGameController() {
        return gameController;
    }

    public void disableInput() {
        inputField.setEnabled(false);
        // Puoi anche disabilitare i pulsanti salva/carica o aggiungere un pulsante "Nuova Partita"
    }

    // Metodo per mostrare un enigma o una domanda che richiede input all'utente
    public void displayPuzzle(String puzzleId, String puzzleText) {
        String answer = JOptionPane.showInputDialog(this, puzzleText, "Enigma dell'Accademia", JOptionPane.QUESTION_MESSAGE);
        if (answer != null) {
            gameController.solvePuzzle(puzzleId, answer.trim());
        } else {
            displayMessage("Hai ignorato la domanda. Il tempo passa...");
        }
    }

    // Metodo per chiedere la combinazione dei sigilli al boss
    public void requestSealCombination() {
        String combination = JOptionPane.showInputDialog(this,
                "Il Custode di Cenere ti attacca! Inserisci la combinazione di Sigilli (es. saggezza vita percezione memoria) per affrontarlo:",
                "Combattimento con il Custode", JOptionPane.QUESTION_MESSAGE);
        if (combination != null) {
            gameController.processCommand("affronta " + combination.trim()); // Un nuovo comando interno per il boss
        } else {
            displayMessage("Hai esitato... il Custode si rafforza e ti colpisce!");
            // Potresti qui applicare una penalità o un game over immediato se non si risponde.
        }
    }
}