package com.example.demo.UI.dialogs;

import com.example.demo.Core.Player;
import com.example.demo.Data.PlayerData;
import com.example.demo.Mapping.PlayerMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProfileSetupDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<String> jobBox;
    private JPanel[] imagePanels;
    private ImageIcon[] profileImages;
    private String[] imagePaths = {
            "C:\\Users\\kis\\OneDrive - Dr. Glinz COVIS GmbH\\IU\\Java 2\\assets\\profile1.png",
            "C:\\Users\\kis\\OneDrive - Dr. Glinz COVIS GmbH\\IU\\Java 2\\assets\\profile2.png",
            "C:\\Users\\kis\\OneDrive - Dr. Glinz COVIS GmbH\\IU\\Java 2\\assets\\profile3.png"
    };

    private Player createdPlayer;            // Das erzeugte Spielerobjekt
    private int selectedImageIndex = -1;     // Merkt sich das ausgewählte Bild


     //Öffnet ein modales Dialogfenster zur Erstellung eines Spielerprofils.
    public ProfileSetupDialog(JFrame parent) {
        super(parent, "Profil erstellen", true);
        setLayout(new BorderLayout());

        // Bilder laden und skalieren
        profileImages = new ImageIcon[imagePaths.length];
        for (int i = 0; i < imagePaths.length; i++) {
            ImageIcon originalIcon = new ImageIcon(imagePaths[i]);
            Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            profileImages[i] = new ImageIcon(scaledImage);
        }

        // Oberer Bereich: Name und Job-Auswahl
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Job:"));
        jobBox = new JComboBox<>(new String[] { "Entwickler", "Projekt Manager", "Consultant" });
        inputPanel.add(jobBox);

        // Mittlerer Bereich: Profilbild-Auswahl
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 3));

        imagePanels = new JPanel[3];
        for (int i = 0; i < 3; i++) {
            imagePanels[i] = new JPanel();
            JLabel imageLabel = new JLabel(profileImages[i]);
            imagePanels[i].add(imageLabel);
            imagePanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            // Auswahl-Feedback beim Klicken
            int finalI = i;
            imagePanels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (JPanel panel : imagePanels) {
                        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                    }
                    imagePanels[finalI].setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                    selectedImageIndex = finalI;
                }
            });

            imagePanel.add(imagePanels[i]);
        }

        // Unterer Bereich: Erstellen-Button
        JButton confirmButton = new JButton("Erstellen");
        confirmButton.addActionListener(this::createPlayer);

        // GUI zusammensetzen
        add(inputPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true); // Dialog anzeigen
    }


     // Erstellt das Player-Objekt aus Nutzereingaben.
     // Validiert vorher die Eingaben (Name + Bild).
    private void createPlayer(ActionEvent e) {
        String name = nameField.getText().trim();
        String job = (String) jobBox.getSelectedItem();

        if (name.isEmpty() || selectedImageIndex == -1) {
            JOptionPane.showMessageDialog(this, "Bitte Name eingeben und Bild auswählen.");
            return;
        }

        String imagePath = imagePaths[selectedImageIndex];

        // Spieler-Datenstruktur erzeugen (DTO)
        PlayerData playerData = new PlayerData(name, job, 0, 1, imagePath);
        // In echtes Player-Objekt umwandeln (Domänenobjekt)
        Player player = PlayerMapper.toDomain(playerData);
        createdPlayer = player;

        dispose(); // Dialog schließen
    }


     // Gibt das erstellte Player-Objekt zurück.
    public Player getCreatedPlayer() {
        return createdPlayer;
    }
}