package com.example.demo.UI.components;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

import com.example.demo.Core.Leaderboard;
import com.example.demo.Core.Player;
import com.example.demo.Controller.LeaderboardController;
import com.example.demo.UI.theme.ThemeManager;

public class LeaderboardPanel extends JPanel {

    private JList<String> leaderboardList;                 // Visuelle Liste der Spieler
    private LeaderboardController controller;              // Lädt Spielerprofile

    private DefaultListModel<String> listModel;            // Datenmodell für die JList
    private JScrollPane scrollPane;                        // Scrollbarer Bereich

    private Leaderboard leaderboard;                       // Interne Repräsentation (sortiert)


     // Konstruktor: Initialisiert das Panel mit GUI-Komponenten und lädt die Rangliste.
    public LeaderboardPanel(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        this.controller = new LeaderboardController();

        // Model und JList initialisieren
        listModel = new DefaultListModel<>();
        leaderboardList = new JList<>(listModel);
        leaderboardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leaderboardList.setVisibleRowCount(10);

        scrollPane = new JScrollPane(leaderboardList); // Scroll-Funktion für lange Listen

        // GUI-Layout festlegen
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Rangliste"));
        add(scrollPane, BorderLayout.CENTER);

        updateLeaderboard(); // Spieler laden und anzeigen
        refreshTheme();      // Theme anwenden (Farben, Schrift)
    }

    
     // Lädt alle gespeicherten Spieler, sortiert sie nach Level,
     // überträgt sie in das Leaderboard-Objekt und aktualisiert die GUI.
    public void updateLeaderboard() {
        listModel.clear(); // Alte Einträge entfernen

        // Spielerprofile laden
        List<Player> players = controller.loadAllPlayers();

        // Spieler nach Level absteigend sortieren
        players.sort(Comparator.comparingInt(Player::getGameLevel).reversed());

        // Interne Rangliste leeren und neu aufbauen
        leaderboard.clear();
        for (Player p : players) {
            leaderboard.addPlayer(p);
        }

        // GUI-Liste aktualisieren
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            String entry = String.format("Rang %d: %s (Level %d, %d Punkte)",
                    i + 1, p.getName(), p.getGameLevel(), p.getScore());
            listModel.addElement(entry);
        }
    }


     // Wendet das aktuelle Farb-Theme auf alle Komponenten an.
    public void refreshTheme() {
        ThemeManager.applyTheme(this);
        ThemeManager.applyTheme(leaderboardList);
        ThemeManager.applyTheme(scrollPane);

        // TitledBorder-Farbe anpassen
        if (getBorder() instanceof javax.swing.border.TitledBorder) {
            ((javax.swing.border.TitledBorder) getBorder())
                .setTitleColor(ThemeManager.getTextColor());
        }

        repaint(); // Neuzeichnung anstoßen
    }
}