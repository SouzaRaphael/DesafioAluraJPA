package br.com.exaulra.screensound.main;

import br.com.exaulra.screensound.models.Artist;
import br.com.exaulra.screensound.models.Music;
import br.com.exaulra.screensound.models.RArtist;
import br.com.exaulra.screensound.models.RMusic;
import br.com.exaulra.screensound.repository.ArtistRepository;
import br.com.exaulra.screensound.services.ApiResponseToClass;
import br.com.exaulra.screensound.services.GetApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final ArtistRepository ARTIST_REPOSITORY;
    private final Scanner IN = new Scanner(System.in);

    private final String ADDRESS_ARTIST = "https://www.vagalume.com.br/";
    private final String ADDRESS_MUSIC = "https://api.vagalume.com.br/";

    public Main(ArtistRepository artistRepository) {
        this.ARTIST_REPOSITORY = artistRepository;
    }

    public void menu() {
        int opcao;

        loop: while (true) {
            System.out.print("""
                    ***** ScreenSound *****
                    
                    1 - Cadastrar artistas
                    2 - Cadastrar musicas
                    3 - Listar artistas
                    4 - Listar musicas
                    5 - Buscar musicas por artista
                    6 - Sair
                    
                    Digite sua opcao: """);

            opcao = IN.nextInt();
            IN.nextLine();

            switch (opcao) {
                case 1:
                    registerArtist();
                    break;
                case 2:
                    registerMusic();
                    break;
                case 3:
                    listArtists();
                    break;
                case 4:
                    listMusics();
                    break;
                case 5:
                    searchMusicByArtist();
                    break;
                case 6:
                    break loop;
            }
        }
    }

    private void registerArtist() {
        System.out.print("Digite o nome do artista: ");
        String artistName = IN.nextLine();

        String json = GetApiResponse.getResponse(ADDRESS_ARTIST + artistName + "/index.js");
        RArtist rArtist = ApiResponseToClass.responseToRArtist(json);

        System.out.print("Digite o tipo do artista (solo, dupla, banda): ");
        String type = IN.next();

        Artist artist = new Artist(rArtist, type);
        ARTIST_REPOSITORY.save(artist);
    }

    private void registerMusic() {
        System.out.print("Digite o nome do artista: ");
        String artistName = IN.nextLine();

        Artist foundArtist = searchArtistInDBByName(artistName);
        if (foundArtist == null)
            return;

        System.out.print("Digite o nome da musica do artista: ");
        String musicName = IN.nextLine();

        String json = GetApiResponse.getResponse(ADDRESS_MUSIC + "search.artmus?q=" + artistName + "%20" + musicName.replace(" ", "%20"));
        RMusic rMusic = ApiResponseToClass.responseToRMusic(json);

        Music music = new Music(rMusic);
        foundArtist.addMusic(music);
        ARTIST_REPOSITORY.save(foundArtist);
    }

    private void listArtists() {
        List<Artist> artists = ARTIST_REPOSITORY.listArtists();
        artists.forEach(System.out::println);
    }

    private void listMusics() {
        List<Music> musics = ARTIST_REPOSITORY.listMusics();
        musics.forEach(System.out::println);
    }

    private void searchMusicByArtist() {
        System.out.print("Digite o nome do artista: ");
        String artistName = IN.nextLine();

        Artist foundArtist = searchArtistInDBByName(artistName);
        if (foundArtist == null)
            return;

        List<Music> musics = ARTIST_REPOSITORY.listMusicByArtist(foundArtist);
        musics.forEach(System.out::println);
    }

    private Artist searchArtistInDBByName(String artistName) {
        Optional<Artist> artist = Optional.ofNullable(ARTIST_REPOSITORY.findByNameContainingIgnoreCase(artistName));
        if (artist.isEmpty()) {
            System.out.println("Artista cadastrado nao encontrado.");
            return null;
        }
        return artist.get();
    }
}