package com.example.demo.Data;

import java.io.Serializable;

public class PlayerData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String job;
    private int score;
    private int gameLevel;
    private String profilePicturePath;

    public PlayerData(String name, String job, int score, int gameLevel, String profilePicturePath) {
        this.name = name;
        this.job = job;
        this.score = score;
        this.gameLevel = gameLevel;
        this.profilePicturePath = profilePicturePath;
    }

    // Getter
    public String getName() { return name; }
    public String getJob() { return job; }
    public int getScore() { return score; }
    public int getGameLevel() { return gameLevel; }
    public String getProfilePicturePath() { return profilePicturePath; }

    // Setter
    public void setName(String name) { this.name = name; }
    public void setJob(String job) { this.job = job; }
    public void setScore(int score) { this.score = score; }
    public void setGameLevel(int gameLevel) { this.gameLevel = gameLevel; }
    public void setProfilePicturePath(String profilePicturePath) { this.profilePicturePath = profilePicturePath; }
}
