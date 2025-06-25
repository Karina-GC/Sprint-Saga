/* package com.example.demo.UI.components;

import com.example.demo.Core.Player;
import com.example.demo.UI.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoPanel extends JPanel {

    private JLabel nameLabel;
    private JLabel jobLabel;
    private JLabel levelLabel;
    private JLabel scoreLabel;
    private JLabel profileImageLabel;

    public PlayerInfoPanel(Player player) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(160, 180));
        setBorder(BorderFactory.createLineBorder(ThemeManager.getAccentColor(), 1));

        // Bild
        profileImageLabel = new JLabel(getScaledImage(player));
        profileImageLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Labels
        nameLabel = new JLabel(player.getName());
        jobLabel = new JLabel("Job: " + player.getJob());
        levelLabel = new JLabel("Level: " + player.getGameLevel());
        scoreLabel = new JLabel("Punkte: " + player.getScore());

        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        jobLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        jobLabel.setAlignmentX(CENTER_ALIGNMENT);
        levelLabel.setAlignmentX(CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Aufbau
        add(Box.createVerticalStrut(8));
        add(profileImageLabel);
        add(Box.createVerticalStrut(6));
        add(nameLabel);
        add(jobLabel);
        add(levelLabel);
        add(scoreLabel);

        ThemeManager.applyTheme(this);
    }

    private ImageIcon getScaledImage(Player player) {
        ImageIcon icon = player.getProfilePicture();
        if (icon != null) {
            Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } else {
            return new ImageIcon("images/defaultProfile.png");
        }
    }
}
 */