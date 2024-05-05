package com.eldarian.resourceservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MetadataSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataSenderService.class);
    private final RestTemplate restTemplate;
    @Value("${songservice.baseAddress}")
    private String songServiceAddress;

    @Autowired
    public MetadataSenderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMetadata(String metadata) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(metadata, headers);

        // URL for metadata sending
        String url = songServiceAddress + "/songs";

        // Sending the request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Processing the response if needed
        if (response.getStatusCode().is2xxSuccessful()) {
            LOGGER.info("Metadata sent successfully");
        } else {
            LOGGER.info("Failed to send metadata. Status code: " + response.getStatusCode());
        }
    }
}
