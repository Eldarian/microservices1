package com.eldarian.songservice.service;

import com.eldarian.songservice.model.Song;
import com.eldarian.songservice.repository.SongRepository;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.serialization.JsonMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

        Optional.ofNullable(metadata.get("dc:title")).ifPresent(song::setTitle);
        Optional.ofNullable(metadata.get("xmpDM:album")).ifPresent(song::setAlbum);
        Optional.ofNullable(metadata.get("xmpDM:artist")).ifPresent(song::setArtist);
        Optional.ofNullable(metadata.get("xmpDM:genre")).ifPresent(song::setGenre);

        Optional.ofNullable(metadata.get("xmpDM:releaseDate")).ifPresent(releaseDate -> {
            try {
                song.setYear(Integer.parseInt(releaseDate));
            } catch (NumberFormatException e) {
                song.setYear(0);
            }
        });

        Optional.ofNullable(metadata.get("resourceId")).ifPresent(resourceId -> {
            try {
                song.setResourceId(Long.parseLong(resourceId));
            } catch (NumberFormatException e) {
                // handle the exception, e.g. set a default value or throw a custom exception
            }
        });

        Optional.ofNullable(metadata.get("xmpDM:duration")).ifPresent(duration -> {
            try {
                song.setDuration(Double.parseDouble(duration));
            } catch (NumberFormatException e) {
                // handle the exception, e.g. set a default value or throw a custom exception
            }
        });
        Song result = songRepository.save(song);
        return result.getId();
    }

    public String getSongJSON(Long id) throws NoSuchElementException {
        Song song = songRepository.findSongById(id).orElseThrow(NoSuchElementException::new);
        return song.toJSON();
    }

    public void deleteById(List<Long> idList) {
        idList.forEach(this::deleteById);
    }

    public void deleteById(long id) {
        songRepository.deleteById(id);
    }
}
