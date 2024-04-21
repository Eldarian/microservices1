package com.eldarian.resourceservice.model;

import jakarta.persistence.*;

@Entity
public class Mp3File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private byte[] fileData;


    // Getters and setters

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public byte[] getFileData() {
        return fileData;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mp3File mp3File = (Mp3File) obj;
        return id.equals(mp3File.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    // toString
    @Override
    public String toString() {
        return "Mp3File{" +
                "id=" + id +
                '}';
    }
}
