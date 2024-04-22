package com.eldarian.resourceservice.controller;


import com.eldarian.resourceservice.model.Mp3File;
import com.eldarian.resourceservice.service.Mp3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
public class Mp3Controller {

    @Autowired
    private Mp3FileService mp3FileService;

    @PostMapping("/resources")
    public ResponseEntity<String> uploadMp3(@RequestParam("file") MultipartFile file) {
        //TODO implement file validation to distinct server errors from client errors
        try {

            long id = mp3FileService.processMp3File(file);

            return ResponseEntity.ok("{\"id\": " + id + "}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to read file");
        }

    }

    @GetMapping("/resources/{id}")
    public ResponseEntity<byte[]> downloadMp3(@RequestParam("id") Long id) {

        Optional<Mp3File> result = mp3FileService.findById(id);
        return result.map(mp3File -> ResponseEntity.ok(mp3File.getFileData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
