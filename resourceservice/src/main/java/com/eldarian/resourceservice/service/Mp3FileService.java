package com.eldarian.resourceservice.service;

import com.eldarian.resourceservice.model.Mp3File;
import com.eldarian.resourceservice.repository.Mp3FileRepository;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.serialization.JsonMetadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class Mp3FileService {


    private final Mp3FileRepository mp3FileRepository;
    private final MetadataSenderService metadataSenderService;

    @Autowired
    public Mp3FileService(Mp3FileRepository mp3FileRepository, MetadataSenderService metadataSenderService) {
        this.mp3FileRepository = mp3FileRepository;
        this.metadataSenderService = metadataSenderService;
    }



    /**
     * Processes MultipartFile on a service layer. Saves mp3 file to database as blob, then processes metadata
     * @param file MultipartFile to process
     * @return file id in resource db.
     */
    public long processMp3File(MultipartFile file) throws IOException, TikaException, SAXException {
            Mp3File mp3File = new Mp3File();
            byte[] fileData = file.getBytes();

            mp3File.setFileData(fileData);
            Mp3File savedFile = mp3FileRepository.save(mp3File);
            Long fileId = savedFile.getId();
            String result = getMetadata(fileData, fileId);
            sendMetadata(result);
            return fileId;
    }

    protected String getMetadata(byte[] fileData, long resourceId) throws IOException, TikaException, SAXException {
            File tempFile = File.createTempFile("temp-", ".mp3");
            try(FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                fileOutputStream.write(fileData);
                fileOutputStream.flush();
            }
            Metadata metadata = new Metadata();
            try (InputStream input = new FileInputStream(tempFile)) {
                BodyContentHandler handler = new BodyContentHandler();
                Parser mp3Parser = new Mp3Parser();
                mp3Parser.parse(input, handler, metadata, new ParseContext());
            }

            metadata.add("resourceId", String.valueOf(resourceId));

            StringWriter writer = new StringWriter();
            JsonMetadata.toJson(metadata, writer);

            return writer.toString();

    }

    private void sendMetadata(String metadata) {
        System.out.println("Sending metadata: " + metadata);
        metadataSenderService.sendMetadata(metadata);
    }

    public Optional<Mp3File> findById(long id) {
        return mp3FileRepository.findById(id);
    }

    public void deleteById(List<Long> idList) {
        idList.forEach(this::deleteById);
    }

    public void deleteById(long id) {
        mp3FileRepository.deleteById(id);
    }
}
