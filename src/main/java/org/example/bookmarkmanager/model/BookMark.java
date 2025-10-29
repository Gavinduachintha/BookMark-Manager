package org.example.bookmarkmanager.model;

import java.net.URI;
import java.net.URISyntaxException;

public class BookMark {
    private String title;
    private String url;
    private String image;
    private String description;

    public BookMark() {
        // no-args constructor needed for Jackson / JSON deserialization
    }

    public BookMark(String title, String url){
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Added helper to validate the URL format using URI (avoids deprecated URL(String))
    public boolean isValidUrl() {
        if (this.url == null || this.url.trim().isEmpty()) return false;
        try {
            URI uri = new URI(this.url);
            return uri.getScheme() != null && uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }
    @Override
    public String toString() {
        return title + " -> " + url;
    }
}
