package com.eldarian.resourceservice.service;

import com.eldarian.resourceservice.model.Mp3File;
import com.eldarian.resourceservice.repository.Mp3FileRepository;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class Mp3FileServiceTest {

    @Mock
    private Mp3FileRepository mp3FileRepository;

    @Mock
    private MetadataSenderService metadataSenderService;

    private Mp3FileService mp3FileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mp3FileService = new Mp3FileService(mp3FileRepository, metadataSenderService);
    }

    @Test
    void testGetMetadata() throws IOException, TikaException, SAXException {
        File file = new ClassPathResource("test.mp3").getFile();
        byte[] fileData = FileCopyUtils.copyToByteArray(file);
        long resourceID = 1L;

        String result = mp3FileService.getMetadata(fileData, resourceID);
        System.out.println(result);
        // Now you can make assertions about the result
        assertNotNull(result);
        assertTrue(result.contains("title"));
        // Add more assertions based on what you expect the result to be
    }
}