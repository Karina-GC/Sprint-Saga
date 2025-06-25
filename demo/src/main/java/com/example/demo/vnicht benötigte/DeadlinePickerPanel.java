/* package com.example.demo.UI;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class DeadlinePickerPanel extends JPanel {
    private JSpinner dateSpinner;
    private JLabel dateLabel;

    public DeadlinePickerPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Abgabefrist setzen"));

        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);

        dateLabel = new JLabel("Abgabedatum:");
        add(dateLabel, BorderLayout.WEST);
        add(dateSpinner, BorderLayout.CENTER);

        refreshTheme(); // Theme direkt anwenden
    }

    public Date getSelectedDate() {
        return (Date) dateSpinner.getValue();
    }

    public void refreshTheme() {
        ThemeManager.applyTheme(this);
        ThemeManager.applyTheme(dateSpinner);
        ThemeManager.applyTheme(dateLabel);

        // 🛠 Textfeld im DateEditor manuell anpassen
        JComponent editorComponent = dateSpinner.getEditor();
        if (editorComponent instanceof JSpinner.DateEditor) {
            JFormattedTextField tf = ((JSpinner.DateEditor) editorComponent).getTextField();
            tf.setOpaque(true);
            tf.setForeground(ThemeManager.getTextColor());
            tf.setBackground(ThemeManager.getBackgroundColor());
            tf.setCaretColor(ThemeManager.getTextColor());
            tf.setBorder(BorderFactory.createLineBorder(ThemeManager.getAccentColor()));
        }

        // 🛠 TitledBorder ebenfalls im Theme-Stil setzen
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(ThemeManager.getAccentColor()),
            "Abgabefrist setzen", 0, 0, null, ThemeManager.getTextColor()
        ));

        repaint();
    }

}
 */