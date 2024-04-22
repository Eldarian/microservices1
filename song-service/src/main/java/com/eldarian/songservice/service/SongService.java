package com.eldarian.songservice.service;

import com.eldarian.songservice.model.Song;
import com.eldarian.songservice.repository.SongRepository;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.serialization.JsonMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;

@Service
public class SongService {

    SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public long processMetadata(String jsonMetadata) throws IOException {
        Metadata metadata = JsonMetadata.fromJson(new StringReader(jsonMetadata));
        Song song = new Song();
        song.setTitle(metadata.get("dc:title"));
        song.setAlbum(metadata.get("xmpDM:album"));
        song.setArtist(metadata.get("xmpDM:artist"));
        song.setGenre(metadata.get("xmpDM:genre"));
        song.setYear(Integer.parseInt(metadata.get("xmpDM:releaseDate")));
        song.setResourceId(Long.parseLong(metadata.get("resourceId")));
        song.setDuration(Double.parseDouble(metadata.get("xmpDM:duration")));
        Song result = songRepository.save(song);
        return result.getId();
    }

    public String getSongJSON(Long id) throws NoSuchElementException {
        Song song = songRepository.findSongById(id).orElseThrow(NoSuchElementException::new);
        return song.toJSON();
    }
}
