package org.example.bookmarkmanager.service;

import org.example.bookmarkmanager.manager.BookMarkManger;
import org.example.bookmarkmanager.model.BookMark;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class BookMarkService {

    private final BookMarkManger bookMarkManger;

    public BookMarkService() {
        this.bookMarkManger = new BookMarkManger();
    }

    /**
     * Validate and lightly normalize a bookmark URL.
     * If the URL is missing a scheme, try adding http:// and validate again.
     * Throws IllegalArgumentException if the URL is invalid.
     * Saves the bookmark to the JSON file.
     */
    public BookMark processBookMark(BookMark bookMark) {
        if (bookMark == null) throw new IllegalArgumentException("bookmark must not be null");

        String title = bookMark.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title must not be empty");
        }
        bookMark.setTitle(title.trim());

        String url = bookMark.getUrl();
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL must not be empty");
        }

        url = url.trim();

        // First try as-is
        if (isValidUrl(url)) {
            bookMark.setUrl(url);
            bookMarkManger.addBookmark(bookMark);
            return bookMark;
        }

        // If missing scheme, try adding http://
        if (!url.matches("^[a-zA-Z][a-zA-Z0-9+.-]*://.*$")) {
            String withHttp = "http://" + url;
            if (isValidUrl(withHttp)) {
                bookMark.setUrl(withHttp);
                bookMarkManger.addBookmark(bookMark);
                return bookMark;
            }
        }

        throw new IllegalArgumentException("Invalid URL: " + bookMark.getUrl());
    }

    private boolean isValidUrl(String url) {
        try {
            URI uri = new URI(url);
            return uri.getScheme() != null && uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
