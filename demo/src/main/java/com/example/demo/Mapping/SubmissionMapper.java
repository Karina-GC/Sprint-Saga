package com.example.demo.Mapping;

import com.example.demo.Core.Player;
import com.example.demo.Core.Submission;
import com.example.demo.Data.SubmissionData;
import com.example.demo.Data.PlayerDataManager;

import java.util.ArrayList;
import java.util.List;

public class SubmissionMapper {

    // Einzelnes Submission -> SubmissionData
    public static SubmissionData toData(Submission submission) {
        return new SubmissionData(
            submission.getFilePath(),
            submission.getSubmittedBy().getName(),
            submission.getNote(),
            submission.isReviewed()
        );
    }

    // Liste von Submissions -> SubmissionData
    public static List<SubmissionData> toDataList(List<Submission> submissions) {
        List<SubmissionData> dataList = new ArrayList<>();
        for (Submission s : submissions) {
            dataList.add(toData(s));
        }
        return dataList;
    }

    // Einzelnes SubmissionData -> Submission
    public static Submission toCore(SubmissionData data) {
        Player player = PlayerDataManager.getPlayerByName(data.getSubmittedByName());
        if (player == null) {
            System.err.println("⚠ Spieler '" + data.getSubmittedByName() + "' nicht gefunden.");
            return null;
        }

        Submission submission = new Submission(data.getFilePath(), player);
        submission.setNote(data.getNote());
        submission.setReviewed(data.isReviewed());

        return submission;
    }

    // Liste von SubmissionData -> Submission
    public static List<Submission> toCoreList(List<SubmissionData> dataList) {
        List<Submission> result = new ArrayList<>();
        for (SubmissionData data : dataList) {
            Submission s = toCore(data);
            if (s != null) {
                result.add(s);
            }
        }
        return result;
    }
}
