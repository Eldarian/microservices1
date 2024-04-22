package com.eldarian.songservice.controller;

import com.eldarian.songservice.service.SongService;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class SongServiceController {

    SongService songService;

    @Autowired
    public SongServiceController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping("/songs")
    public ResponseEntity<String> uploadSong(@RequestBody Metadata metadata) {
        long songId = songService.processMetadata(metadata);
        return ResponseEntity.ok("{\"id\": " + songId + "}");
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<String> getSong(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(songService.getSongJSON(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Invalid file");
    }
}
