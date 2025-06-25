package com.example.demo.UI.screens;

import javax.swing.*;
import java.awt.*;

import com.example.demo.Controller.GameFrameController;
import com.example.demo.Core.*;

import com.example.demo.UI.theme.ThemeManager;
import com.example.demo.UI.components.CodeDropPanel;
import com.example.demo.UI.components.DeveloperNotePanel;
import com.example.demo.UI.components.LeaderboardPanel;
import com.example.demo.UI.components.LevelBarPanel;
import com.example.demo.UI.components.ProfilePanel;



public class GameFrame extends JFrame {

    private static final String LOGO_PATH = "C:\\Users\\kis\\OneDrive - Dr. Glinz COVIS GmbH\\IU\\Java 2\\assets\\sprint_saga_logo.png";

    private Player player;
    private LevelBarPanel levelBarPanel;
    private ProfilePanel profilePanel;
    private Leaderboard leaderboard;
    private LeaderboardPanel leaderboardPanel;
    private JPanel centerPanel;
    private DeveloperNotePanel developerNotePanel;
    private GameFrameController controller;

    public GameFrame(Player player) {
        this.player = player;
        this.leaderboard = new Leaderboard();
        this.controller = new GameFrameController(player, leaderboard);
        ThemeManager.setTheme(ThemeManager.Theme.DARK);

        setTitle("Sprint-Saga");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());

        addComponents();
        setJMenuBar(createMenuBar());
        applyCurrentTheme();
        setVisible(true);
    }

    private void addComponents() {
        leaderboard = new Leaderboard();
        leaderboard.addPlayer(player);
        leaderboardPanel = new LeaderboardPanel(leaderboard);

        levelBarPanel = new LevelBarPanel(player);
        profilePanel = new ProfilePanel(player);

        player.addScoreListener(() -> levelBarPanel.updateProgress());
        player.addScoreListener(() -> profilePanel.update(player));

        centerPanel = new JPanel(new BorderLayout());
        updateCenterPanel();

        getContentPane().removeAll();
        add(centerPanel, BorderLayout.CENTER);

        // Logo
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(new Color(173, 216, 230));

        ImageIcon originalLogo = new ImageIcon(LOGO_PATH);
        Image scaledLogo = originalLogo.getImage().getScaledInstance(125, 100, Image.SCALE_SMOOTH);
        logoPanel.add(new JLabel(new ImageIcon(scaledLogo)));
        logoPanel.add(Box.createVerticalStrut(10));

        JLabel textLabel = new JLabel("Sprint-Saga");
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoPanel.add(textLabel);
        add(logoPanel, BorderLayout.WEST);

        // Oben
        JPanel levelBarContainer = new JPanel(new BorderLayout());
        levelBarContainer.add(levelBarPanel, BorderLayout.CENTER);
        add(levelBarContainer, BorderLayout.NORTH);

        // Rechts
        add(profilePanel, BorderLayout.EAST);

        // Unten (nur Entwickler)
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

        if (player.getJob().toLowerCase().contains("entwickler")) {
            developerNotePanel = new DeveloperNotePanel(player);
            southPanel.add(developerNotePanel);
        }

        add(southPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void applyCurrentTheme() {
        ThemeManager.applyTheme(this.getContentPane());

        if (getJMenuBar() != null) {
            ThemeManager.applyTheme(getJMenuBar());
        }

        profilePanel.refreshTheme();
        levelBarPanel.refreshTheme();
        leaderboardPanel.refreshTheme();

        if (developerNotePanel != null) {
            developerNotePanel.refreshTheme();
        }

        Component center = centerPanel.getComponentCount() > 0 ? centerPanel.getComponent(0) : null;
        if (center instanceof CodeDropPanel) {
            ((CodeDropPanel) center).refreshTheme();
        } else if (center instanceof ReviewPanel) {
            ((ReviewPanel) center).refreshTheme();
        }

        SwingUtilities.updateComponentTreeUI(this);
        this.repaint();
    }

    private void updateCenterPanel() {
        centerPanel.removeAll();
        String job = player.getJob().toLowerCase();

        if (job.contains("entwickler")) {
            centerPanel.add(new CodeDropPanel(player, null), BorderLayout.CENTER);
        } else if (job.contains("manager") || job.contains("consultant")) {
            centerPanel.add(new ReviewPanel(player, levelBarPanel, profilePanel), BorderLayout.CENTER);
        } else {
            JLabel info = new JLabel("Keine spezielle Ansicht für diese Rolle.");
            info.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(info, BorderLayout.CENTER);
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Datei");

        JMenuItem saveItem = new JMenuItem("Speichern unter...");
        saveItem.addActionListener(e -> savePlayer());

        JMenuItem loadItem = new JMenuItem("Profil laden...");
        loadItem.addActionListener(e -> loadPlayer());

        JMenuItem leaderboardItem = new JMenuItem("Rangliste anzeigen");
        leaderboardItem.addActionListener(e -> showLeaderboard());

        JMenuItem deleteItem = new JMenuItem("Spieler löschen...");
        deleteItem.addActionListener(e -> deletePlayer());

        fileMenu.add(deleteItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(leaderboardItem);
        menuBar.add(fileMenu);

        JMenu themeMenu = new JMenu("Design");

        JMenuItem darkModeItem = new JMenuItem("Dark Mode");
        darkModeItem.addActionListener(e -> {
            ThemeManager.setTheme(ThemeManager.Theme.DARK);
            applyCurrentTheme();
        });

        JMenuItem lightModeItem = new JMenuItem("Light Mode");
        lightModeItem.addActionListener(e -> {
            ThemeManager.setTheme(ThemeManager.Theme.LIGHT);
            applyCurrentTheme();
        });

        JMenuItem accentColorItem = new JMenuItem("Akzentfarbe wählen");
        accentColorItem.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Wähle Akzentfarbe", ThemeManager.getAccentColor());
            if (newColor != null) {
                ThemeManager.setAccentColor(newColor);
                repaint();
            }
        });

        themeMenu.add(darkModeItem);
        themeMenu.add(lightModeItem);
        themeMenu.add(accentColorItem);
        menuBar.add(themeMenu);

        return menuBar;
    }

    private void savePlayer() {
        controller.savePlayer(this);
    }

    private void loadPlayer() {
        Player loaded = controller.loadPlayer(this);
        if (loaded != null) {
            this.player = loaded;
            addComponents();
            applyCurrentTheme();
            JOptionPane.showMessageDialog(this, "Profil geladen: " + player.getName());
        }
    }

    private void showLeaderboard() {
        leaderboardPanel.updateLeaderboard();
        JFrame leaderboardFrame = new JFrame("Rangliste");
        leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaderboardFrame.setSize(500, 300);
        leaderboardFrame.add(leaderboardPanel);
        ThemeManager.applyTheme(leaderboardFrame.getContentPane());
        leaderboardFrame.setVisible(true);
    }

    private void deletePlayer() {
        controller.deletePlayer(this);
    }

}
