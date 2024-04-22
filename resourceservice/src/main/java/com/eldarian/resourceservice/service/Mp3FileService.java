package com.eldarian.resourceservice.service;

import com.eldarian.resourceservice.model.Mp3File;
import com.eldarian.resourceservice.repository.Mp3FileRepository;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.serialization.JsonMetadata;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.Optional;

@Service
public class Mp3FileService {


    @Autowired
    private Mp3FileRepository mp3FileRepository;

    /**
     * Processes MultipartFile on a service layer. Saves mp3 file to database as blob, then processes metadata
     * @param file MultipartFile to process
     * @return file id in resource db.
     */
    public long processMp3File(MultipartFile file) throws IllegalArgumentException, TikaException, SAXException {
        try {
            Mp3File mp3File = new Mp3File();
            byte[] fileData = file.getBytes();
            String metadata = getMetadata(fileData);

            mp3File.setFileData(fileData);
            Mp3File savedFile = mp3FileRepository.save(mp3File);
            Long fileId = savedFile.getId();

            String result = "{ id:"+ fileId + "body:" + metadata + "}";

            return fileId;
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private String getMetadata(byte[] fileData) throws IOException, TikaException, SAXException {
            File tempFile = File.createTempFile("temp-", ".mp3");
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(fileData);
            fileOutputStream.close();

            InputStream input = new FileInputStream(tempFile);
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            Mp3Parser mp3Parser = new Mp3Parser();
            mp3Parser.parse(input, handler, metadata);

            StringWriter writer = new StringWriter();
            JsonMetadata.toJson(metadata, writer);

            return writer.toString();

    }

    public Optional<Mp3File> findById(long id) {
        return mp3FileRepository.findById(id);
    }
}
