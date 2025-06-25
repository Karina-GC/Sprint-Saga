package com.example.demo.UI.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import com.example.demo.Core.Player;
import com.example.demo.UI.theme.ThemeManager;

public class ProfilePanel extends JPanel {

    private JLabel nameLabel;
    private JLabel jobLabel;
    private JLabel profileImageLabel;
    private JLabel scoreLabel;
    private JLabel gameLevelLabel;

    public ProfilePanel(Player player) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(220, 0));

        TitledBorder border = BorderFactory.createTitledBorder("Spielerprofil");
        border.setTitleColor(ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ? Color.WHITE : Color.BLACK);
        setBorder(border);

        profileImageLabel = new JLabel(getScaledProfileImage(player));
        profileImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameLabel = new JLabel("Name: " + player.getName());
        jobLabel = new JLabel("Job: " + player.getJob());
        scoreLabel = new JLabel("Punkte: " + player.getScore());
        gameLevelLabel = new JLabel("Spiel-Level: " + player.getGameLevel());

        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameLevelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(10));
        add(profileImageLabel);
        add(Box.createVerticalStrut(10));
        add(nameLabel);
        add(jobLabel);
        add(scoreLabel);
        add(gameLevelLabel);

        ThemeManager.applyTheme(this);
    }

    public void update(Player player) {

        nameLabel.setText("Name: " + player.getName());
        jobLabel.setText("Job: " + player.getJob());
        scoreLabel.setText("Punkte: " + player.getScore());
        gameLevelLabel.setText("Spiel-Level: " + player.getGameLevel());
        profileImageLabel.setIcon(getScaledProfileImage(player));

        repaint();
    }

    private ImageIcon getScaledProfileImage(Player player) {
        ImageIcon rawIcon = player.getProfilePicture();

        if (rawIcon != null) {
            Image scaledImage = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            ImageIcon defaultIcon = new ImageIcon("images/defaultProfile.png");
            Image scaledDefault = defaultIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledDefault);
        }
    }

    public void refreshTheme() {
        ThemeManager.applyTheme(this);

        if (getBorder() instanceof TitledBorder) {
            ((TitledBorder) getBorder()).setTitleColor(
                ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ? Color.WHITE : Color.BLACK
            );
        }

        repaint();
    }
}
