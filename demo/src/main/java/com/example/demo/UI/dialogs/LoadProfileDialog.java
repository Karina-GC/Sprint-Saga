// Paketstruktur – UI/Dialog-Komponente
package com.example.demo.UI.dialogs;

// Import von Projektklassen (Domäne, DTO, Mapper)
import com.example.demo.Core.Player;
import com.example.demo.Data.PlayerData;
import com.example.demo.Data.PlayerDataLoader;
import com.example.demo.Mapping.PlayerMapper;

// Swing für GUI
import javax.swing.*;
// AWT für Layout
import java.awt.*;
// File für Dateizugriff
import java.io.File;

/**
 * GUI-Dialog zum Laden eines gespeicherten Spielerprofils.
 * Zeigt vorhandene .json-Dateien im Ordner "saves/" zur Auswahl an.
 */
public class LoadProfileDialog extends JDialog {

    // Hier wird der geladene Spieler gespeichert
    private Player loadedPlayer;

    /**
     * Konstruktor: Öffnet den Dialog als modales Fenster
     * @param parent Das übergeordnete Fenster (z. B. GameFrame)
     */
    public LoadProfileDialog(JFrame parent) {
        super(parent, "Profil laden", true); // Titel & Modalität
        setLayout(new BorderLayout());       // Layoutmanager setzen

        // Holt alle gespeicherten Dateien (z. B. Max.json)
        File[] files = PlayerDataLoader.getAllSaveFiles();
        if (files == null || files.length == 0) {
            // Wenn keine Dateien vorhanden sind, Hinweis zeigen und Dialog schließen
            JOptionPane.showMessageDialog(this, "Keine gespeicherten Profile gefunden.");
            dispose();
            return;
        }

        // Dateinamen extrahieren und ins Dropdown-Menü laden
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName();
        }

        // Auswahlmenü mit Profilen
        JComboBox<String> profileComboBox = new JComboBox<>(fileNames);
        // Button zum Laden
        JButton loadButton = new JButton("Laden");

        // Wenn Button geklickt wird: Profil laden
        loadButton.addActionListener(e -> {
            String selected = (String) profileComboBox.getSelectedItem();
            if (selected != null) {
                // 1. Lade JSON-Daten (DTO)
                PlayerData data = PlayerDataLoader.loadFromFile("saves/" + selected);
                // 2. Wandle DTO → Domäne (Player)
                loadedPlayer = PlayerMapper.toDomain(data);
                // 3. Dialog schließen
                dispose();
            }
        });

        // GUI zusammensetzen
        add(new JLabel("Wähle ein gespeichertes Profil:"), BorderLayout.NORTH);
        add(profileComboBox, BorderLayout.CENTER);
        add(loadButton, BorderLayout.SOUTH);

        // Größe anpassen & Dialog zentrieren
        pack();
        setLocationRelativeTo(parent);
        setVisible(true); // Dialog anzeigen
    }

    /**
     * Gibt den erfolgreich geladenen Spieler zurück (nach Dialogschluss)
     */
    public Player getLoadedPlayer() {
        return loadedPlayer;
    }
}
