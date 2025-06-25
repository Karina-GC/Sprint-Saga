/* package com.example.demo.Core;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Badge {
    private String name;
    private String description;
    private String imagePath;
    private ImageIcon icon;

    public Badge(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.icon = loadScaledIcon(imagePath);
        this.imagePath = imagePath;
    }

    private ImageIcon loadScaledIcon(String path) {
        if (path == null || path.isEmpty()) return null;
        ImageIcon rawIcon = new ImageIcon(path);
        Image scaled = rawIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Badge)) return false;
        Badge other = (Badge) obj;
        return name.equals(other.name); // vergleiche nur den Namen
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
 */