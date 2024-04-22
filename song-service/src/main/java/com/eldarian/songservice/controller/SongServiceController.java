package com.eldarian.songservice.controller;

import com.eldarian.songservice.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    @DeleteMapping("/songs")
    public ResponseEntity<String> deleteMp3(@RequestParam("id") String id) {
        List<Long> idList = Arrays.stream(id.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        songService.deleteById(idList);
        return ResponseEntity.ok("{ids: " + idList + "}");
    }
}
