package com.example.demo.Controller;

import com.example.demo.Core.Player;
import com.example.demo.Data.JsonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardController {       // Diese Klasse lädt alle gespeicherten Spielerprofile

    public List<Player> loadAllPlayers() {      // Gibt eine Liste von Player-Objekten zurück
        List<Player> players = new ArrayList<>();   // ➤ Erstellt eine leere Liste für die geladenen Spieler
        File saveDir = new File("saves");    // ➤ Erstellt ein File-Objekt für den "saves/"-Ordner

        if (saveDir.exists() && saveDir.isDirectory()) {    // Ordner existiert und ist Verzeichnis ?
            File[] files = saveDir.listFiles((dir, name) -> name.endsWith(".json"));    // Holt alle Dateien im Verzeichnis, die auf ".json" enden
            if (files != null) {
                for (File file : files) {
                    Player loaded = JsonUtils.loadPlayer(file.getAbsolutePath());   // Lädt die Datei als Player-Objekt
                    if (loaded != null) {
                        players.add(loaded);
                    }
                }
            }
        }

        return players;
    }
}
