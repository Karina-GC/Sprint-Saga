package com.example.demo.Controller;

import com.example.demo.Core.Player;
import com.example.demo.Core.Submission;
import com.example.demo.Data.JsonUtils;
import com.example.demo.Data.PendingSubmissions;
import com.example.demo.Data.SubmissionDataManager;

import java.io.File;

public class SubmissionController {

    private final Player currentPlayer;     // aktiver Spieler

    public SubmissionController(Player currentPlayer) {
        this.currentPlayer = currentPlayer;     //Konstruktor, aktiver Spieler
    }

    public boolean submitFile(File file) {      // Gibt true zurück bei Erfolg, false wenn Datei ungültig
        if (file == null || !file.getName().endsWith(".java")) {    // Validierung: nur .java-Dateien sind erlaubt
            return false;
        }

        String filePath = file.getAbsolutePath();       // Holt den vollständigen Pfad der Datei
        Submission submission = new Submission(filePath, currentPlayer);    // Erstellt ein neues Submission-Objekt mit Datei + Entwickler
        submission.setReviewed(false);          // Markiert die Abgabe als „noch nicht bewertet“

        PendingSubmissions.getInstance().addSubmission(submission);      // Fügt die Submission zur Liste der offenen Abgaben hinzu (Singleton)
        SubmissionDataManager.saveSubmissions(PendingSubmissions.getInstance().getSubmissions());   // Persistiert alle Submissions auf Festplatte

        currentPlayer.addScore(1);
        JsonUtils.savePlayer(currentPlayer, "saves/" + currentPlayer.getName() + ".json");      // Speichert den aktualisierten Punktestand des Spielers

        return true;
    }
}
