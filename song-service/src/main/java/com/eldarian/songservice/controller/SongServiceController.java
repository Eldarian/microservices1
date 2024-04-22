package com.eldarian.songservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongServiceController {

    @PostMapping("/songs")
    public ResponseEntity<String> uploadSong() {
        return ResponseEntity.ok("Song uploaded");
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<String> getSong(@RequestParam("id") Long id) {
        return ResponseEntity.ok("Song");
    }
}
