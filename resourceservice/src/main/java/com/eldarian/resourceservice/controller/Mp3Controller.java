package com.eldarian.resourceservice.controller;


import com.eldarian.resourceservice.model.Mp3File;
import com.eldarian.resourceservice.service.Mp3FileService;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class Mp3Controller {

    private final Mp3FileService mp3FileService;

    @Autowired
    public Mp3Controller(Mp3FileService mp3FileService) {
        this.mp3FileService = mp3FileService;
    }

    @PostMapping("/resources")
    public ResponseEntity<String> uploadMp3(@RequestParam("file") MultipartFile file) {
        //TODO implement file validation to distinct server errors from client errors
        System.out.println("Received file: " + file.getOriginalFilename());
        try {

            long id = mp3FileService.processMp3File(file);

            return ResponseEntity.ok("{\"id\": " + id + "}");
        } catch (SAXException e) {
            return ResponseEntity.internalServerError().body("Failed to parse metadata");
        } catch (TikaException e) {
            return ResponseEntity.internalServerError().body("Failed to extract metadata");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to read file");
        }

    }

    @GetMapping("/resources/{id}")
    public ResponseEntity<byte[]> downloadMp3(@PathVariable("id") Long id) {

        Optional<Mp3File> result = mp3FileService.findById(id);
        return result.map(mp3File -> ResponseEntity.ok(mp3File.getFileData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Invalid file");
    }

    @DeleteMapping("/resources")
    public ResponseEntity<String> deleteMp3(@RequestParam("id") String id) {
        List<Long> idList = Arrays.stream(id.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        mp3FileService.deleteById(idList);
        return ResponseEntity.ok("{ids: " + idList + "}");
    }

}
