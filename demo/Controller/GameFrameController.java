package com.example.demo.Controller;

import com.example.demo.Core.Leaderboard;
import com.example.demo.Core.Player;
import com.example.demo.Data.JsonUtils;

import javax.swing.*;
import java.io.File;

public class GameFrameController {      // Steuert das Hauptfenster: Laden, Speichern und Löschen von Spielerprofilen

    private final Player player;
    private final Leaderboard leaderboard;       // Speichert den aktuellen Spieler und die zugehörige Rangliste

    public GameFrameController(Player player, Leaderboard leaderboard) {
        this.player = player;
        this.leaderboard = leaderboard;         // Konstruktor: bekommt Spieler + Leaderboard übergeben und speichert sie lokal
    }  

    public void savePlayer(JFrame parent) {      // Dateiauswahldialog, um ein Spielerprofil zu speichern
        JFileChooser chooser = new JFileChooser("saves/");
        chooser.setDialogTitle("Spielerprofil speichern");
        chooser.setSelectedFile(new File(player.getName() + ".json"));

        int result = chooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {         // Wenn der Benutzer "Speichern" klickt
            JsonUtils.savePlayer(player, chooser.getSelectedFile().getAbsolutePath());  // Speichert den Spieler als JSON-Datei
            JOptionPane.showMessageDialog(parent, "Spielerprofil gespeichert.");
        }
    }

    public Player loadPlayer(JFrame parent) {       // Öffnet Datei-Dialog, um ein gespeichertes Spielerprofil zu laden
        JFileChooser chooser = new JFileChooser("saves/");
        chooser.setDialogTitle("Profil laden");

        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {     // Wenn Benutzer eine Datei ausgewählt hat
            File file = chooser.getSelectedFile();
            Player loaded = JsonUtils.loadPlayer(file.getAbsolutePath());       //lädt Spieler

            if (loaded != null) {
                leaderboard.clear();
                JsonUtils.loadLeaderboard(leaderboard, "saves/leaderboard.json");
                leaderboard.addPlayer(loaded);
                return loaded;
            } else {
                JOptionPane.showMessageDialog(parent, "Laden fehlgeschlagen.");
            }
        }
        return null;
    }

    public void deletePlayer(JFrame parent) {           // Ermöglicht dem Benutzer, ein gespeichertes Profil zu löschen
        JFileChooser chooser = new JFileChooser("saves/");
        chooser.setDialogTitle("Spielerprofil löschen");

        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            int confirm = JOptionPane.showConfirmDialog(parent,
                    "Möchtest du dieses Profil wirklich löschen?\n" + file.getName(),
                    "Löschen bestätigen", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION && file.delete()) {
                JOptionPane.showMessageDialog(parent, "Spielerprofil gelöscht.");
            } else {
                JOptionPane.showMessageDialog(parent, "Löschen fehlgeschlagen.");
            }
        }
    }
}
