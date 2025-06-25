package com.example.demo.Controller;

import com.example.demo.Core.Player;
import com.example.demo.Core.Submission;
import com.example.demo.Data.JsonUtils;
import com.example.demo.Data.PendingSubmissions;
import com.example.demo.Mapping.PlayerMapper;

import java.util.List;

import javax.swing.*;

public class ReviewController {    


    private final Player reviewer;

    public ReviewController(Player reviewer) {
        this.reviewer = reviewer;   // Konstruktor, setzt fest
    }

    public boolean approveSubmission(Submission submission, String note, JFrame parent) {   // Bewertet einzelne Submission und speichert das Feedback
                                                                                            // Gibt true zurück bei Erfolg, sonst false
        if (submission == null || note == null || note.trim().isEmpty()) {                  // Überprüft, ob keine Datei oder leere Notiz
            JOptionPane.showMessageDialog(parent, "Datei und Notiz dürfen nicht leer sein.");
            return false;
        }

        Player developer = submission.getSubmittedBy();     // Holt den Entwickler, der die Datei eingereicht hat

        // Punktevergabe
        developer.addScore(3);       // Entwickler bekommt 3 Punkte
        reviewer.addScore(2);        // Reviewer bekommt 2 Punkte

        // Submission-Status setzen
        submission.setReviewed(true);       // Als geprüft markieren
        submission.setNote(note.trim());             // Notiz setzen
        submission.setNoteRead(false);      // Notiz ist noch nicht gelesen


        // Daten speichern
        JsonUtils.savePlayerData(PlayerMapper.toData(developer), "saves/" + developer.getName() + ".json");
        JsonUtils.savePlayerData(PlayerMapper.toData(reviewer), "saves/" + reviewer.getName() + ".json");
        PendingSubmissions.getInstance().saveToDisk();      // Speichert alle aktuellen Submissions inkl. Bewertung

        JOptionPane.showMessageDialog(parent,
                "Feedback gesendet an " + developer.getName() +
                "\n+3 Punkte für Entwickler\n+2 Punkte für dich!");

        return true;
    }

    public List<Submission> getPendingSubmissions() {           // Gibt alle noch nicht bewerteten Submissions zurück
        return PendingSubmissions.getInstance().getSubmissions()
            .stream()
            .filter(s -> !s.isReviewed())
            .toList();
            // Filtert alle Submissions, die noch nicht als "reviewed" markiert sind
    }

}
