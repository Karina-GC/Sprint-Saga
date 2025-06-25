package com.example.demo.Core;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private String job;
    private int score;
    private int gameLevel;

    private String profilePicturePath;
    private transient ImageIcon profilePicture;

    private transient List<Runnable> scoreListeners = new ArrayList<>();

    public Player(String name, String job, String profilePicturePath) {
        this.name = name;
        this.job = job;
        this.score = 0;
        this.gameLevel = 1;
        this.profilePicturePath = profilePicturePath;
        loadProfilePicture();
    }

    public void loadProfilePicture() {
        if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
            this.profilePicture = new ImageIcon(profilePicturePath);
        }
    }

    public void addScore(int points) {
        score += points;
        gameLevel = 1 + (score / 5);
        notifyScoreListeners();
    }

    public void addScoreListener(Runnable listener) {
        scoreListeners.add(listener);
    }

    private void notifyScoreListeners() {
        for (Runnable listener : scoreListeners) {
            listener.run();
        }
    }

    // --- Getter ---
    public String getName() { return name; }
    public String getJob() { return job; }
    public int getScore() { return score; }
    public int getGameLevel() { return gameLevel; }
    public String getProfilePicturePath() { return profilePicturePath; }
    public ImageIcon getProfilePicture() { return profilePicture; }

    // --- Setter ---
    public void setScore(int score) {
        this.score = score;
        this.gameLevel = 1 + (score / 5);
    }

    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }


    public void setProfilePicture(ImageIcon profilePicture) {
        this.profilePicture = profilePicture;
    }
}
