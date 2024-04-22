package com.eldarian.songservice.model;

import jakarta.persistence.*;

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

    public Song() {
    }

    public Song(Long id, String title, String artist, String album, String genre, int year, byte[] fileData) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.year = year;
    }
}
