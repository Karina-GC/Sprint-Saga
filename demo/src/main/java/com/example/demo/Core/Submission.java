package com.example.demo.Core;

import java.io.Serializable;

public class Submission implements Serializable {
    private String filePath; 
    private String note;
    private boolean reviewed;
    private boolean noteRead;
    private Player submittedBy;

    public Submission(String filePath, Player submittedBy) {
        this.filePath = filePath;
        this.submittedBy = submittedBy;
        this.reviewed = false;
        this.noteRead = false;
    }

    public String getFilePath() {
        return filePath;
    }

    public Player getSubmittedBy() {
        return submittedBy;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String getNote() {
        return note != null ? note : "";
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isNoteRead() {
        return noteRead;
    }

    public void setNoteRead(boolean noteRead) {
        this.noteRead = noteRead;
    }

    public void setSubmittedBy(Player player) {
        this.submittedBy = player;
    }

    @Override
    public String toString() {
        return filePath + (reviewed ? " ✔" : "");
    }
    .
}
