package com.example.demo.Data;

import com.example.demo.Core.Player;
import java.util.*;

public class PlayerDataManager {
    private static final String FILE_PATH = "data/players.json";    // Standardpfad zur zentralen Spielerdatei
    private static final Map<String, Player> playerMap = new HashMap<>();    // Interner Speicher aller Spieler, nach Name indiziert (Cache)

    public static void savePlayers(Collection<Player> players) {
        JsonUtils.save(players, FILE_PATH);  // Speichert alle übergebenen Spieler in einer JSON-Datei
        playerMap.clear();                   // Löscht
        for (Player p : players) {
            playerMap.put(p.getName(), p);   // Aktualisiert den Cache mit den neuen Spielern (indiziert nach Name)
        }
    }
    
    public static List<Player> loadPlayers() { 
        List<Player> players = JsonUtils.loadList(FILE_PATH, Player.class); // Lädt Liste von Playern aus JSON-Datei
        playerMap.clear();

        for (Player p : players) {
            p.loadProfilePicture();
            playerMap.put(p.getName(), p);  // lädt das Profilbild aus Pfad (nicht serialisiert)
        }
        return players;
    }

    public static Player getPlayerByName(String name) {   // Gibt einen bestimmten Spieler direkt aus der Map zurück (Cache-Zugriff)
        return playerMap.get(name);
    }
}
