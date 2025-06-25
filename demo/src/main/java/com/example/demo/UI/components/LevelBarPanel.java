package com.example.demo.UI.components;

import javax.swing.*;
import java.awt.*;

import com.example.demo.Core.Player;
import com.example.demo.UI.theme.ThemeManager;

public class LevelBarPanel extends JPanel {
    private Player player;
    private JProgressBar progressBar;
    private JLabel levelLabel;

    public LevelBarPanel(Player player) {
        this.player = player;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 50));  // Höhe 50px

        // Level-Anzeige
        levelLabel = new JLabel("Level: " + player.getGameLevel());
        levelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Fortschrittsbalken: 0–5 Punkte für 1 Level
        progressBar = new JProgressBar(0, 5);
        int currentProgress = player.getScore() % 5;
        progressBar.setValue(currentProgress);
        progressBar.setStringPainted(true);
        progressBar.setString(currentProgress + "/5 Punkte im Level");

        add(levelLabel, BorderLayout.WEST);
        add(progressBar, BorderLayout.CENTER);
    }

    public void updateProgress() {
        System.out.println("⚙️ updateProgress() aufgerufen. Score: " + player.getScore());

        int currentProgress = player.getScore() % 5;
        levelLabel.setText("Level: " + player.getGameLevel());
        progressBar.setValue(currentProgress);
        progressBar.setString(currentProgress + "/5 Punkte im Level");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ThemeManager.applyTheme(this);
    }

    public void refreshTheme() {
        ThemeManager.applyTheme(this);
        ThemeManager.applyTheme(levelLabel);
        ThemeManager.applyTheme(progressBar);
        repaint();
    }
}
