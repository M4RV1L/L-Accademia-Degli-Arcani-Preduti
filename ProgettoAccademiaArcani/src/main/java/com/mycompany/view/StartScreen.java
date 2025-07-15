/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author marcovillano
 */
public class StartScreen extends JFrame {
    private JButton newGameButton;
    private JButton loadGameButton;
    private JTextField playerNameField;
    private JLabel titleLabel;
    private JLabel nameLabel;

    public StartScreen() {
        setTitle("L'Accademia degli Arcani Perduti");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra
        
        setLayout(new BorderLayout(10, 10));

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("L'Accademia degli Arcani Perduti");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        northPanel.add(titleLabel);
        add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout()); // Usa GridBagLayout per un controllo più preciso
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spazio tra i componenti

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        nameLabel = new JLabel("Nome Avventuriero:");
        centerPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        playerNameField = new JTextField(15);
        centerPanel.add(playerNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Occupa due colonne
        gbc.fill = GridBagConstraints.HORIZONTAL; // Riempi orizzontalmente
        newGameButton = new JButton("Nuova Partita");
        centerPanel.add(newGameButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        loadGameButton = new JButton("Carica Partita");
        centerPanel.add(loadGameButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        newGameButton.addActionListener(e -> {
            String playerName = playerNameField.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Per favore, inserisci il tuo nome.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose(); // Chiudi la schermata iniziale
                new GameGUI(playerName); // Avvia la GUI del gioco
            }
        });

        loadGameButton.addActionListener(e -> {
            dispose(); // Chiudi la schermata iniziale
            GameGUI gameGUI = new GameGUI(null); // Passa null per indicare che è un caricamento
            gameGUI.getGameController().loadGame();
        });

        setVisible(true);
    }
}