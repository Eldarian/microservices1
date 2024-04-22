package com.eldarian.resourceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class MetadataSenderService {

    private final RestTemplate restTemplate;

    @Autowired
    public MetadataSenderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMetadata(Map<String, String> metadata, Long fileId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(metadata, headers);

        // URL для отправки запроса
        String url = "http://example.com/api/metadata/" + fileId;

        // Отправляем запрос и обрабатываем ответ
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Обработка ответа, если необходимо
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Metadata sent successfully");
        } else {
            System.out.println("Failed to send metadata. Status code: " + response.getStatusCodeValue());
        }
    }
}
