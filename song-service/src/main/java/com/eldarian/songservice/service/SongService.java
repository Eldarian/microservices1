package com.eldarian.songservice.service;

import com.eldarian.songservice.model.Song;
import com.eldarian.songservice.repository.SongRepository;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SongService {

    SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public long processMetadata(Metadata metadata) {
        Song song = new Song();
        song.setTitle(metadata.get("dc:title"));
        song.setAlbum(metadata.get("xmpDM:album"));
        song.setArtist(metadata.get("xmpDM:artist"));
        song.setGenre(metadata.get("xmpDM:genre"));
        song.setYear(Integer.parseInt(metadata.get("xmpDM:releaseDate")));
        song.setResourceId(Long.parseLong(metadata.get("resourceId")));
        song.setDuration(Integer.parseInt(metadata.get("xmpDM:duration")));
        Song result = songRepository.save(song);
        return result.getId();
    }

    public String getSongJSON(Long id) throws NoSuchElementException {
        Song song = songRepository.findSongById(id).orElseThrow(NoSuchElementException::new);
        return song.toJSON();
    }
}
