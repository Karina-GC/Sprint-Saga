package com.example.demo.Data;

import com.example.demo.Core.Submission;
import com.example.demo.Mapping.SubmissionMapper;

import java.util.ArrayList;
import java.util.List;

public class SubmissionDataManager {    // persistente Speicherung offener (pending) Submissions
    private static final String FILE_PATH = "data/pending_submissions.json";    // Der Pfad zur JSON-Datei, in der alle offenen Submissions gespeichert werden

    public static void saveSubmissions(List<Submission> submissions) {
        List<SubmissionData> dataList = new ArrayList<>();  // Erstellt eine neue Liste für SubmissionData-Objekte (DTOs)
        for (Submission s : submissions) {      // Wandelt jede Submission in ein SubmissionData-Objekt um (für Speicherung)
            dataList.add(SubmissionMapper.toData(s));
        }
        JsonUtils.save(dataList, FILE_PATH);    // Speichert die Liste der SubmissionData als JSON-Datei
    }

    public static List<Submission> loadSubmissions() {    
        List<SubmissionData> dataList = JsonUtils.loadList(FILE_PATH, SubmissionData.class);    // Lädt die Liste der SubmissionData-Objekte aus JSON-Datei
        List<Submission> result = new ArrayList<>();    // Neue Liste für die konvertierten Submission-Objekte
        if (dataList != null) {
            for (SubmissionData d : dataList) {
                result.add(SubmissionMapper.toCore(d));     // ➤ Wandelt jedes DTO zurück in ein echtes Submission-Objekt
            }
        }
        return result;
    }
}
