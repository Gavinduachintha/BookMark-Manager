package org.example.bookmarkmanager.manager;
import org.example.bookmarkmanager.model.BookMark;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookMarkManger {
    private List<BookMark> bookmarks; // in-memory list
    private final String filePath = "bookmarks.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public BookMarkManger() {
        this.bookmarks = loadBookmarks(); // load bookmarks from file
    }

    // Add a bookmark
    public void addBookmark(BookMark bookmark) {
        bookmarks.add(bookmark);
        saveBookmarks();
    }

    // Delete a bookmark by title
    public void deleteBookmark(String title) {
        bookmarks.removeIf(b -> b.getTitle().equalsIgnoreCase(title));
        saveBookmarks();
    }

    // Get all bookmarks
    public List<BookMark> getAllBookmarks() {
        return bookmarks;
    }

    // Save bookmarks to JSON file using Jackson
    private void saveBookmarks() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), bookmarks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load bookmarks from JSON file using Jackson (read as an array to avoid TypeReference)
    private List<BookMark> loadBookmarks() {
        File f = new File(filePath);
        if (!f.exists()) return new ArrayList<>();
        try {
            BookMark[] arr = mapper.readValue(f, BookMark[].class);
            return arr != null ? new ArrayList<>(Arrays.asList(arr)) : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
