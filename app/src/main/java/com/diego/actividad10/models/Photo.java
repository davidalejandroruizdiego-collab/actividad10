package com.diego.actividad10.models;

import java.io.Serializable;

public class Photo implements Serializable {

    // Tus campos finales
    private final int id;
    private final String title;
    private final String description;
    private final String date;
    private final String imageUrl; // La ruta o URL de la imagen es un String

    private boolean isSelected = false;

    public Photo() {
        this.id = 0;
        this.title = "";
        this.description = "";
        this.date = "";
        this.imageUrl = "";
    }

    @SuppressWarnings("ConstructorMayBeIncomplete")
    public Photo(int id, String title, String description, String date, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
