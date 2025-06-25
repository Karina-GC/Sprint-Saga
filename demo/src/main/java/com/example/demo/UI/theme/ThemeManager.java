package com.example.demo.UI.theme;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.JTextComponent;

 // Der ThemeManager ist eine zentrale Utility-Klasse,
 // die das aktuelle Farbthema (z. B. Dark Mode) der Anwendung verwaltet.
 // Er stellt Methoden bereit, um Farben auf alle GUI-Komponenten anzuwenden.
 
public class ThemeManager {

    // Unterstützte Themes
    public enum Theme {
        LIGHT, DARK
    }

    // Aktuell eingestelltes Theme
    private static Theme currentTheme = Theme.LIGHT;

    // Akzentfarbe (z. B. für Rahmen, Buttons)
    private static Color accentColor = new Color(30, 144, 255); // Dodger Blue

    // Aktuelle Hintergrund- und Textfarben (werden je nach Theme gesetzt)
    private static Color currentBackground = Color.WHITE;
    private static Color currentTextColor = Color.BLACK;

    
     // Wendet das aktuelle Farbthema auf eine Swing-Komponente an.
     // Diese Methode wird rekursiv auch auf Kindkomponenten angewendet.
    public static void applyTheme(Component component) {
        Color bg = currentBackground;
        Color fg = currentTextColor;

        // Anpassen je nach Komponententyp
        if (component instanceof JLabel) {
            component.setForeground(fg);
        } else if (component instanceof AbstractButton || component instanceof JTextComponent) {
            component.setBackground(bg);
            component.setForeground(fg);
        } else if (component instanceof JPanel || component instanceof JScrollPane) {
            component.setBackground(bg);
        } else {
            component.setBackground(bg);
            component.setForeground(fg);
        }

        // Spezialfall: Inhalt eines ScrollPanes (z. B. JTextArea in JScrollPane)
        if (component instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) component;
            Component view = scrollPane.getViewport().getView();
            if (view != null) {
                applyTheme(view);
            }
        }

        // Rekursiv alle Kindkomponenten ebenfalls anpassen
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyTheme(child);
            }
        }
    }


     // Setzt das globale Theme (LIGHT oder DARK) und die entsprechenden Farben.
     // Muss nach Änderung manuell per applyTheme(...) auf Komponenten angewendet werden.
    public static void setTheme(Theme theme) {
        currentTheme = theme;

        if (theme == Theme.DARK) {
            currentBackground = new Color(40, 40, 40); // Dunkelgrau
            currentTextColor = Color.WHITE;
        } else {
            currentBackground = Color.WHITE;
            currentTextColor = Color.BLACK;
        }
    }

    // Getter für aktuelles Theme
    public static Theme getCurrentTheme() {
        return currentTheme;
    }

    // Setzt die Akzentfarbe (für z. B. Rahmen oder Überschriften)
    public static void setAccentColor(Color color) {
        accentColor = color;
    }

    // Getter für Akzent- und Text-/Hintergrundfarbe
    public static Color getAccentColor() {
        return accentColor;
    }

    public static Color getTextColor() {
        return currentTextColor;
    }

    public static Color getBackgroundColor() {
        return currentBackground;
    }
}