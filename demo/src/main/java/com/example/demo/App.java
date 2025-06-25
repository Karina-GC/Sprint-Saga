package com.example.demo;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.example.demo.Data.JsonUtils;
import com.example.demo.Core.Player;

import com.example.demo.UI.dialogs.LoadProfileDialog;
import com.example.demo.UI.dialogs.ProfileSetupDialog;
import com.example.demo.UI.screens.GameFrame;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Zentrales Hauptfenster (unsichtbar)
            JFrame mainFrame = new JFrame("Sprint Saga");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800, 600); // Größe setzen
            mainFrame.setLocationRelativeTo(null); // Bildschirmzentriert
           
            // Auswahl: Neues Spiel oder laden
            String[] options = { "Neues Spiel", "Bestehendes Profil laden" };
            int choice = JOptionPane.showOptionDialog(
                    mainFrame,
                    "Willkommen zur Sprint-Saga! Was möchtest du tun?",
                    "Spiel starten",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            Player player = null;

            if (choice == 0) {
                // Neues Spiel
                ProfileSetupDialog dialog = new ProfileSetupDialog(mainFrame);
                player = dialog.getCreatedPlayer();

                if (player != null) {
                    JsonUtils.savePlayer(player, "saves/" + player.getName() + ".json");
                }

            } else if (choice == 1) {
                // Bestehendes Profil laden
                LoadProfileDialog loadDialog = new LoadProfileDialog(mainFrame);
                player = loadDialog.getLoadedPlayer();
            }

            // Wenn ein Spieler erfolgreich erstellt oder geladen wurde
            if (player != null) {
                new GameFrame(player);
            } else {
                System.out.println("Kein Spiel gestartet.");
                System.exit(0);
            }
        });
    }
}



