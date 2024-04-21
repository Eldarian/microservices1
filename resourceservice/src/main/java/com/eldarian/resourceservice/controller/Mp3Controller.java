package com.eldarian.resourceservice.controller;


import com.eldarian.resourceservice.repository.Mp3FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class Mp3Controller {

    @Autowired
    private Mp3FileRepository mp3FileRepository;

    @PostMapping("/resources")
    public ResponseEntity<String> uploadMp3(@RequestParam("file") MultipartFile file) {
        return null;
    }

    @GetMapping("/resources/{id}")
    public ResponseEntity<byte[]> downloadMp3(@RequestParam("id") Long id) {
        return null;
    }

}
