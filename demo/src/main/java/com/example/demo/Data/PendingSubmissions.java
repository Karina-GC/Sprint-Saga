package com.example.demo.Data;

import com.example.demo.Core.Submission;
import java.util.*;

public class PendingSubmissions { 
    private static PendingSubmissions instance;     // Singleton-Instanz (nur ein Objekt dieser Klasse zur Laufzeit erlaubt)
    private final List<Submission> submissions = new ArrayList<>(); // Interne Liste, in der alle eingereichten Submissions gespeichert werden

    private PendingSubmissions() {}     // Privater Konstruktor – verhindert direkte Instanziierung (wegen Singleton)

    public static PendingSubmissions getInstance() {    // Liefert die einzige Instanz zurück (lazy initialization)
        if (instance == null) {
            instance = new PendingSubmissions();
        }
        return instance;
    }

    public void addSubmission(Submission s) { submissions.add(s); } // Fügt eine neue Submission zur Liste hinzu
    public void removeSubmission(Submission s) { submissions.remove(s); }   // Entfernt
    public List<Submission> getSubmissions() {          //  Gibt eine **nicht veränderbare** Kopie der Liste zurück
        return Collections.unmodifiableList(submissions);   // damit von außen niemand direkt manipulieren kann
    }

    public void saveToDisk() {      // Speichert die aktuelle Liste der Submissions auf die Festplatte (z. B. als JSON-Datei)
        SubmissionDataManager.saveSubmissions(submissions);
    }

    public void loadFromDisk() {   // Lädt gespeicherte Submissions von der Festplatte und ersetzt die aktuelle Liste damit
        submissions.clear();
        submissions.addAll(SubmissionDataManager.loadSubmissions());
    }
}
