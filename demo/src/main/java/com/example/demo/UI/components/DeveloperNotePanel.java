package com.example.demo.UI.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

import com.example.demo.Controller.DeveloperNoteController;
import com.example.demo.Core.Player;
import com.example.demo.Core.Submission;

import com.example.demo.UI.theme.ThemeManager;

public class DeveloperNotePanel extends JPanel {

    private DeveloperNoteController controller;

    private DefaultListModel<Submission> noteModel;
    private JList<Submission> noteList;
    private JTextArea noteContentArea;
    private JButton markAsReadButton;

    private JScrollPane listScroll;
    private JScrollPane contentScroll;

    public DeveloperNotePanel(Player developer) {
        this.controller = new DeveloperNoteController(developer);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Rückmeldungen von Reviewern"));

        noteModel = new DefaultListModel<>();
        noteList = new JList<>(noteModel);
        noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listScroll = new JScrollPane(noteList);
        listScroll.setBorder(BorderFactory.createTitledBorder("Neue Rückmeldungen"));

        noteContentArea = new JTextArea(5, 20);
        noteContentArea.setEditable(false);
        noteContentArea.setLineWrap(true);
        noteContentArea.setWrapStyleWord(true);
        contentScroll = new JScrollPane(noteContentArea);
        contentScroll.setBorder(BorderFactory.createTitledBorder("Inhalt"));

        markAsReadButton = new JButton("Als gelesen markieren");
        markAsReadButton.addActionListener(e -> markNoteAsRead());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(contentScroll, BorderLayout.CENTER);
        bottomPanel.add(markAsReadButton, BorderLayout.SOUTH);

        add(listScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        noteList.addListSelectionListener(e -> {
            Submission selected = noteList.getSelectedValue();
            noteContentArea.setText(selected != null ? selected.getNote() : "");
        });

        refreshNotes();
        refreshTheme();
    }

    private void markNoteAsRead() {
        Submission selected = noteList.getSelectedValue();
        if (selected == null) return;

        controller.markNoteAsRead(selected);
        noteModel.removeElement(selected);
        JOptionPane.showMessageDialog(this, "Notiz als gelesen markiert.");
    }


    public void refreshNotes() {
        noteModel.clear();
        List<Submission> notes = controller.getUnreadNotes();
        for (Submission s : notes) {
            noteModel.addElement(s);
        }
    }

    public void refreshTheme() {
        ThemeManager.applyTheme(this);
        ThemeManager.applyTheme(noteList);
        ThemeManager.applyTheme(noteContentArea);
        ThemeManager.applyTheme(markAsReadButton);
        ThemeManager.applyTheme(listScroll);
        ThemeManager.applyTheme(contentScroll);

        // Titel einfärben je nach Theme
        if (getBorder() instanceof TitledBorder) {
            ((TitledBorder) getBorder()).setTitleColor(
                ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ? Color.WHITE : Color.BLACK
            );
        }

        if (listScroll.getBorder() instanceof TitledBorder) {
            ((TitledBorder) listScroll.getBorder()).setTitleColor(
                ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ? Color.WHITE : Color.BLACK
            );
        }

        if (contentScroll.getBorder() instanceof TitledBorder) {
            ((TitledBorder) contentScroll.getBorder()).setTitleColor(
                ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK ? Color.WHITE : Color.BLACK
            );
        }

        repaint();
    }
}
