package org.example.bookmarkmanager.controller;

import org.example.bookmarkmanager.model.BookMark;
import org.example.bookmarkmanager.service.BookMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmarks")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @Autowired
    public BookMarkController(BookMarkService bookMarkService) {
        this.bookMarkService = bookMarkService;
    }

    @PostMapping
    public ResponseEntity<?> createBookMark(@RequestBody BookMark bookMark) {
        try {
            BookMark processed = bookMarkService.processBookMark(bookMark);
            return ResponseEntity.ok(processed);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

