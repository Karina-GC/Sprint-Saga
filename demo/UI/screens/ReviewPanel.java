package com.example.demo.UI.screens;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.example.demo.Controller.ReviewController;
import com.example.demo.Core.Player;
import com.example.demo.Core.Submission;

import com.example.demo.UI.components.LevelBarPanel;
import com.example.demo.UI.components.ProfilePanel;
import com.example.demo.UI.theme.ThemeManager;


public class ReviewPanel extends JPanel {
    private final Player reviewer;
    private final DefaultListModel<Submission> submissionModel;
    private final JList<Submission> submissionList;
    private final JScrollPane submissionScroll;
    private final JTextArea noteArea;
    private final JScrollPane noteScroll;
    private final JButton approveButton;
    private final LevelBarPanel levelBarPanel;
    private final ProfilePanel profilePanel;
    private ReviewController reviewController;

    // Konstruktor: Initialisiert das Panel für Codebewertungen.
    public ReviewPanel(Player reviewer, LevelBarPanel levelBarPanel, ProfilePanel profilePanel) {
        this.reviewer = reviewer;
        this.levelBarPanel = levelBarPanel;
        this.profilePanel = profilePanel;
        this.reviewController = new ReviewController(reviewer);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Code-Abgaben bewerten"));

        // Liste aller offenen Submissions
        submissionModel = new DefaultListModel<>();
        submissionList = new JList<>(submissionModel);
        submissionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        submissionScroll = new JScrollPane(submissionList);
        submissionScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ThemeManager.getAccentColor()),
            "Eingereichte Dateien", 0, 0, null, ThemeManager.getTextColor()
        ));

        // Notizfeld für Feedback an Entwickler
        noteArea = new JTextArea(3, 20);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);

        noteScroll = new JScrollPane(noteArea);
        noteScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ThemeManager.getAccentColor()),
            "Notiz für den Entwickler", 0, 0, null, ThemeManager.getTextColor()
        ));

        // Button zur Bewertung (vergibt Punkte, speichert Notiz)
        approveButton = new JButton("Punkte vergeben & Notiz senden");
        approveButton.addActionListener(e -> handleApproval());

        // Unten: Layout für Notiz + Button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(noteScroll, BorderLayout.CENTER);
        bottomPanel.add(approveButton, BorderLayout.SOUTH);

        // Panel zusammensetzen
        add(submissionScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Inhalte und Theme initialisieren
        refreshSubmissions();
        refreshTheme();
    }


     // Wird ausgelöst, wenn der "Bewerten"-Button gedrückt wird.
     // Die ausgewählte Submission wird bearbeitet, Punkte vergeben, Notiz gespeichert.
    private void handleApproval() {
        Submission selected = submissionList.getSelectedValue();
        String note = noteArea.getText().trim();

        boolean success = reviewController.approveSubmission(
            selected, note, (JFrame) SwingUtilities.getWindowAncestor(this)
        );

        if (success) {
            submissionModel.removeElement(selected);    // Abgabe aus Liste entfernen
            noteArea.setText("");                    // Eingabefeld leeren
            levelBarPanel.updateProgress();           // Levelanzeige aktualisieren
            profilePanel.update(reviewer);           // Profilanzeige aktualisieren
        }
    }


     // Holt die aktuelle Liste der offenen Submissions vom Controller
     // und lädt sie in das GUI-Modell.
    public void refreshSubmissions() {
        submissionModel.clear();
        List<Submission> submissions = reviewController.getPendingSubmissions();
        for (Submission s : submissions) {
            submissionModel.addElement(s);
        }
    }

    // Dark-Mode
    public void refreshTheme() {
        ThemeManager.applyTheme(this);
        ThemeManager.applyTheme(submissionScroll);
        ThemeManager.applyTheme(submissionList);
        ThemeManager.applyTheme(noteScroll);
        ThemeManager.applyTheme(noteArea);
        ThemeManager.applyTheme(approveButton);

        // Rahmenfarben aktualisieren
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ThemeManager.getAccentColor()),
            "Code-Abgaben bewerten", 0, 0, null, ThemeManager.getTextColor()
        ));

        submissionScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ThemeManager.getAccentColor()),
            "Eingereichte Dateien", 0, 0, null, ThemeManager.getTextColor()
        ));

        noteScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ThemeManager.getAccentColor()),
            "Notiz für den Entwickler", 0, 0, null, ThemeManager.getTextColor()
        ));

        repaint();
    }
}