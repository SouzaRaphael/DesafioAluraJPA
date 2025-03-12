package br.com.exaulra.screensound.services;

import br.com.exaulra.screensound.models.RArtist;
import br.com.exaulra.screensound.models.RMusic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApiResponseToClass {
    public static RArtist responseToRArtist(String json) {
        try {
            JsonNode artistNode = new ObjectMapper().readTree(json);
            String name = artistNode.get("artist").get("desc").asText();

            List<String> genres = new ArrayList<>();
            artistNode.get("artist").get("genre").elements().forEachRemaining(n -> genres.add(n.get("name").asText() + ", "));

            genres.add(genres.get(genres.size() - 1).replace(", ", ""));
            genres.remove(genres.get(genres.size() - 2));

            String genre = String.join("", genres);
//            String genre = "";
//            artistNode.get("artist").get("genre").elements().forEachRemaining(n -> genre.concat(n.get("name").asText()).concat(", "));
//            String genre = "";
//            artistNode.get("artist").get("genre").findValues("name").forEach(nameNode -> genre.concat(nameNode.asText()).concat(", "));
//            String genre = artistNode.get("artist").get("genre").get(0).get("name").asText();
            return new RArtist(name, genre);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static RMusic responseToRMusic(String json) {
        try {
            JsonNode musicNode = new ObjectMapper().readTree(json);
            String musicTitle = musicNode.get("response").get("docs").get(1).get("band").asText();
            return new RMusic(musicTitle);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}