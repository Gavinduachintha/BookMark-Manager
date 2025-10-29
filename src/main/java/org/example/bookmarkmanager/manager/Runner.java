package org.example.bookmarkmanager.manager;

import org.example.bookmarkmanager.model.BookMark;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        BookMarkManger manager = new BookMarkManger();

        // Add a couple of bookmarks (URLs will be written to bookmarks.json)
        manager.addBookmark(new BookMark("Example", "https://example.com"));
        manager.addBookmark(new BookMark("OpenAI", "https://openai.com"));

        // Print all bookmarks to console
        List<BookMark> all = manager.getAllBookmarks();
        System.out.println("Bookmarks in memory:");
        for (BookMark b : all) {
            System.out.println(b.getTitle() + " -> " + b.getUrl());
        }

        System.out.println("Wrote " + all.size() + " bookmarks to bookmarks.json (working directory).");
    }
}

