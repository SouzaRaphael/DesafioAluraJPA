package br.com.exaulra.screensound.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private String genre;
    @Enumerated(EnumType.STRING)
    private ArtistType type;
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Music> musics = new ArrayList<>();

    public Artist() {
    }

    public Artist(RArtist rArtist, String type) {
        this.name = rArtist.name();
        this.genre = rArtist.genre();
        this.type = ArtistType.fromString(type);
    }

    public void addMusic(Music music) {
        music.setArtist(this);
        this.musics.add(music);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", type=" + type +
                '}';
    }
}