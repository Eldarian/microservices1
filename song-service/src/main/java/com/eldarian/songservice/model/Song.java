package com.eldarian.songservice.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String artist;

    @Column
    private String album;

    @Column
    private String genre;

    @Column
    private int year;

    @Column
    private long resourceId;

    @Column
    private int duration; // duration of the song in seconds

    public Song() {
    }

    public Song(Long id, String title, String artist, String album, String genre, int year, long resourceId, int duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.year = year;
        this.resourceId = resourceId;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationInMinutesAndSeconds() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toJSON() {
        return "{" +
                "\"id\": " + id + "," +
                "\"title\": \"" + title + "\"," +
                "\"artist\": \"" + artist + "\"," +
                "\"album\": \"" + album + "\"," +
                "\"genre\": \"" + genre + "\"," +
                "\"year\": " + year + "," +
                "\"resourceId\": " + resourceId + "," +
                "\"duration\": " + duration +
                "}";
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", resourceId=" + resourceId +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return year == song.year && resourceId == song.resourceId && duration == song.duration && Objects.equals(id, song.id) && Objects.equals(title, song.title) && Objects.equals(artist, song.artist) && Objects.equals(album, song.album) && Objects.equals(genre, song.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, artist, album, genre, year, resourceId, duration);
    }
}
