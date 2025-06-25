/* package com.example.demo.UI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.example.demo.Core.Player;
import com.example.demo.Data.JsonUtils;

public class ActionPanel extends JPanel {

    private Player player;
    private LevelBarPanel levelBarPanel;
    private ProfilePanel profilePanel;

    private LocalDate deadline;
    private JSpinner dateSpinner;

    // → Instanzvariablen für Theme-Zugriff
    private JPanel datePanel;
    private JPanel buttonPanel;
    private JButton submitCodeButton;
    private JButton bugFixButton;
    private JLabel dateLabel;

    public ActionPanel(Player player, LevelBarPanel levelBarPanel, ProfilePanel profilePanel) {
        this.player = player;
        this.levelBarPanel = levelBarPanel;
        this.profilePanel = profilePanel;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Aktionen"));

        // DatePanel
        datePanel = new JPanel(new FlowLayout());
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        dateLabel = new JLabel("Abgabefrist:");

        datePanel.add(dateLabel);
        datePanel.add(dateSpinner);

        // ButtonPanel
        buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        submitCodeButton = new JButton("Code abgegeben");
        bugFixButton = new JButton("Fehler behoben");

        submitCodeButton.addActionListener(e -> handleCodeSubmission());
        bugFixButton.addActionListener(e -> handleBugFix());

        buttonPanel.add(submitCodeButton);
        buttonPanel.add(bugFixButton);

        add(datePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        refreshTheme(); // optional initial aufrufen
    }

    private boolean isManagerOrConsultant() {
        String job = player.getJob().toLowerCase();
        return job.contains("manager") || job.contains("consultant");
    }

    private void handleCodeSubmission() {
        if (!isManagerOrConsultant()) {
            JOptionPane.showMessageDialog(this, "Nur Manager oder Consultant dürfen Code bewerten.");
            return;
        }

        Date selectedDate = (Date) dateSpinner.getValue();
        deadline = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate today = LocalDate.now();
        boolean isOnTime = !today.isAfter(deadline);

        player.addScore(3);

        if (isOnTime) {
            player.addOnTimeSubmission();
            JOptionPane.showMessageDialog(this, "Pünktlich abgegeben! 3 Punkte + Abzeichenfortschritt.");
        } else {
            JOptionPane.showMessageDialog(this, "Verspätet abgegeben. 3 Punkte wurden trotzdem vergeben.");
        }

        // ✅ Punktestand speichern
        JsonUtils.savePlayer(player, "saves/" + player.getName() + ".json");

        updatePanels();
    }

    private void handleBugFix() {
        if (!isManagerOrConsultant()) {
            JOptionPane.showMessageDialog(this, "Nur Manager oder Consultant dürfen Punkte vergeben.");
            return;
        }

        if (player.getJob().equalsIgnoreCase("Entwickler")) {
            player.addScore(2);
            JOptionPane.showMessageDialog(this, "2 Punkte wurden dem Entwickler für die Fehlerbehebung vergeben.");

            // ✅ Punktestand speichern
            JsonUtils.savePlayer(player, "saves/" + player.getName() + ".json");

        } else {
            JOptionPane.showMessageDialog(this, "Nur Entwickler erhalten Punkte für Fehlerbehebung.");
        }

        updatePanels();
    }

    private void updatePanels() {
        profilePanel.update(player);
    }

    public void updatePlayer(Player newPlayer) {
        this.player = newPlayer;
    }

    public void refreshTheme() {
        ThemeManager.applyTheme(this);
        ThemeManager.applyTheme(datePanel);
        ThemeManager.applyTheme(buttonPanel);
        ThemeManager.applyTheme(dateSpinner);
        ThemeManager.applyTheme(dateLabel);
        ThemeManager.applyTheme(submitCodeButton);
        ThemeManager.applyTheme(bugFixButton);

        // Titelrahmen neu setzen
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ThemeManager.getAccentColor()),
            "Aktionen", 0, 0, null, ThemeManager.getTextColor()
        ));

        dateLabel.setForeground(ThemeManager.getTextColor());

        // 🧠 Der kritische Block ↓
        JComponent editor = dateSpinner.getEditor();
        if (editor instanceof JSpinner.DateEditor) {
            JFormattedTextField tf = ((JSpinner.DateEditor) editor).getTextField();
            tf.setOpaque(true);
            tf.setForeground(ThemeManager.getTextColor());
            tf.setBackground(ThemeManager.getBackgroundColor());
            tf.setCaretColor(ThemeManager.getTextColor());
            tf.setBorder(BorderFactory.createLineBorder(ThemeManager.getAccentColor()));
        }

        repaint();
    }



}
 */