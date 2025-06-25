package com.example.demo.Data;

public class SubmissionData {
    private String filePath;
    private String submittedByName;
    private String note;
    private boolean reviewed;

    public SubmissionData() {}

    public SubmissionData(String filePath, String submittedByName, String note, boolean reviewed) {
        this.filePath = filePath;
        this.submittedByName = submittedByName;
        this.note = note;
        this.reviewed = reviewed;
    }

    // Getter und Setter
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSubmittedByName() {
        return submittedByName;
    }

    public void setSubmittedByName(String submittedByName) {
        this.submittedByName = submittedByName;
    }

    public String getNote() {
        return note != null ? note : "";
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }
}
