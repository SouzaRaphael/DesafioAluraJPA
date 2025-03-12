package br.com.exaulra.screensound.models;

import jakarta.persistence.*;

@Entity
@Table(name = "musics")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    @ManyToOne
    private Artist artist;

    public Music() {
    }

    public Music(RMusic rMusic) {
        this.title = rMusic.title();
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", artist=" + artist +
                '}';
    }
}
