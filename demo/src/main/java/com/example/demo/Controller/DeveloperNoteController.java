package com.example.demo.Controller;

import com.example.demo.Core.Player;
import com.example.demo.Core.Submission;
import com.example.demo.Data.PendingSubmissions;

import java.util.List;
import java.util.stream.Collectors;

public class DeveloperNoteController {

    private final Player developer;         // Speichert den Spieler (Entwickler), für den die Notizen gelten

    public DeveloperNoteController(Player developer) {
        this.developer = developer;                 // Konstruktor: erwartet den Entwickler, für den dieser Controller aktiv ist
    }

    public List<Submission> getUnreadNotes() {      // Liefert alle ungelesenen Notizen, die an diesen Entwickler gerichtet sind
        return PendingSubmissions.getInstance().getSubmissions().stream()
                                                    // Holt alle Abgaben (Submissions) aus dem Singleton
                                                    // Startet einen Stream, um sie zu filtern
                .filter(s -> s.isReviewed()
                        && !s.isNoteRead()
                        && s.getSubmittedBy().getName().equals(developer.getName()))
                .collect(Collectors.toList());      // Wandelt den Stream in eine Liste um und gibt sie zurück
    }

    public void markNoteAsRead(Submission submission) {
        if (submission != null) {
            submission.setNoteRead(true);
            PendingSubmissions.getInstance().saveToDisk();      // Speichert die gesamte Liste zurück auf die Festplatte
                                                                // Damit bleibt die Änderung beim nächsten Start erhalten
        }
    }
}
