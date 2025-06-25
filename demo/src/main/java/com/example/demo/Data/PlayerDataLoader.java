package com.example.demo.Data;

import java.io.File;



public class PlayerDataLoader {     // bietet Hilfsmethoden zum Laden von gespeicherten Spielerprofilen

    public static File[] getAllSaveFiles() {
        File folder = new File("saves/");       // Erstellt ein File-Objekt für den Ordner "saves/"
        if (!folder.exists()) folder.mkdirs();          // Falls der Ordner nicht existiert, wird er erstellt
        return folder.listFiles((dir, name) -> name.endsWith(".json"));     // Gibt alle Dateien im Verzeichnis zurück, die auf ".json" enden
    }

    public static PlayerData loadFromFile(String path) {     // Lädt eine PlayerData-Datei über den angegebenen Pfad mit Hilfe von JsonUtils
        return JsonUtils.loadPlayerData(path);
    }
}
