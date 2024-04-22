package com.eldarian.songservice.controller;

import com.eldarian.songservice.service.SongService;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok("Song");
    }
}
