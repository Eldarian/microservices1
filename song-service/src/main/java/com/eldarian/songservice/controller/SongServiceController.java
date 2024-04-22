package com.eldarian.songservice.controller;

import com.eldarian.songservice.service.SongService;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.serialization.JsonMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;

@RestController
public class SongServiceController {

    SongService songService;

    @Autowired
    public SongServiceController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping("/songs")
    public ResponseEntity<String> uploadSong(@RequestBody String jsonMetadata) {
        try {
            System.out.println(jsonMetadata);
            long songId = songService.processMetadata(jsonMetadata);
            return ResponseEntity.ok("{\"id\": " + songId + "}");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to process metadata, " + e.getMessage());
        }

    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<String> getSong(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(songService.getSongJSON(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Invalid file, " + e.getMessage());
    }
}
