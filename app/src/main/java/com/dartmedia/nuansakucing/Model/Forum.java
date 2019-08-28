package com.dartmedia.nuansakucing.Model;

public class Forum {
    private String title;
    private String description;
    private String imageResource;

    public Forum(String title, String description,String imageResource) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
    }
    public Forum(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageResource() {
        return imageResource;
    }
}
