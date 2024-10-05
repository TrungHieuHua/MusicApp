package com.example.myapplication.Model;

public class Favorite {
    private String id;
    private Song song;
    private String uid;

    public Favorite() {
    }

    public Favorite(String id, Song song, String uid) {
        this.id = id;
        this.song = song;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
