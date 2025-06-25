package com.example.demo.UI.components;

import com.example.demo.Controller.SubmissionController;
import com.example.demo.Core.Player;
import com.example.demo.UI.screens.ReviewPanel;
import com.example.demo.UI.theme.ThemeManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.List;


public class CodeDropPanel extends JPanel {

    private JLabel successLabel; // Anzeige für Erfolgsnachricht
    private SubmissionController submissionController; // Controller zur Einreichungsverarbeitung

    /**
     * Konstruktor für das Drop-Panel
     * @param currentPlayer Der aktuell eingeloggte Spieler
     * @param reviewPanel Optional: Panel, das nach erfolgreicher Abgabe aktualisiert werden soll
     */
    public CodeDropPanel(Player currentPlayer, ReviewPanel reviewPanel) {
        this.submissionController = new SubmissionController(currentPlayer);

        // Rahmen mit Titel "Code hierher ziehen"
        TitledBorder border = BorderFactory.createTitledBorder("Code hierher ziehen");
        // Farbanpassung je nach Theme
        border.setTitleColor(ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ? Color.WHITE : Color.BLACK);
        setBorder(border);

        // Größe und Layout festlegen
        setPreferredSize(new Dimension(400, 150));
        setLayout(new BorderLayout());

        // Label zur Anleitung im Panel-Zentrum
        JLabel label = new JLabel("Ziehe hier deine .java Datei rein", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Label für Erfolgsmeldung (initial unsichtbar)
        successLabel = new JLabel("", SwingConstants.CENTER);
        successLabel.setFont(new Font("Arial", Font.BOLD, 16));
        successLabel.setForeground(new Color(0, 128, 0)); // Grün
        successLabel.setVisible(false);
        add(successLabel, BorderLayout.SOUTH);

        // Drag & Drop-Funktionalität aktivieren
        setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                // Nur Datei-Importe erlauben
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            @SuppressWarnings("unchecked")
            public boolean importData(TransferSupport support) {
                try {
                    // Dateien aus dem Drop-Vorgang extrahieren
                    List<File> files = (List<File>) support.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);

                    // Alle .java-Dateien einzeln an den Controller übergeben
                    for (File file : files) {
                        if (submissionController.submitFile(file)) {
                            // Nach erfolgreicher Abgabe Panel aktualisieren (optional)
                            if (reviewPanel != null) {
                                reviewPanel.refreshSubmissions();
                            }
                            // Erfolg anzeigen
                            showSuccessMessage(file.getName());
                        }
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });

        // Theme anwenden (Farben, Hintergrund)
        ThemeManager.applyTheme(this);
    }

    /**
     * Aktualisiert die Farben des Panels, wenn das Theme geändert wurde.
     */
    public void refreshTheme() {
        ThemeManager.applyTheme(this);

        if (getBorder() instanceof TitledBorder) {
            ((TitledBorder) getBorder()).setTitleColor(
                ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ? Color.WHITE : Color.BLACK
            );
        }

        repaint(); // Neuzeichnung des Panels
    }

    /**
     * Zeigt eine temporäre Erfolgsmeldung nach erfolgreicher Datei-Einreichung.
     * @param fileName Name der abgegebenen Datei
     */
    private void showSuccessMessage(String fileName) {
        successLabel.setText("\"" + fileName + "\" erfolgreich eingereicht!");
        successLabel.setVisible(true);

        Color originalColor = getBackground();
        setBackground(new Color(200, 255, 200)); // Hellgrün als visuelle Rückmeldung

        // Nach 1,2 Sekunden zurücksetzen
        new Timer(1200, e -> {
            setBackground(originalColor);
            successLabel.setVisible(false);
        }).start();
    }
}
