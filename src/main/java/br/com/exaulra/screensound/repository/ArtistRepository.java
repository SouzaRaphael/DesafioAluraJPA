package br.com.exaulra.screensound.repository;

import br.com.exaulra.screensound.models.Artist;
import br.com.exaulra.screensound.models.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByNameContainingIgnoreCase(String artistName);
    @Query("select a from Artist a order by a.name")
    List<Artist> listArtists();
    @Query("select m from Artist a join a.musics m order by m.title")
    List<Music> listMusics();
    @Query("select m from Artist a join a.musics m where a = :foundArtist order by m.title")
    List<Music> listMusicByArtist(Artist foundArtist);
}