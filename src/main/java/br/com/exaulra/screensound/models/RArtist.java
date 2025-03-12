package br.com.exaulra.screensound.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RArtist(
        @JsonAlias("desc") String name,
        @JsonAlias("name") String genre
) {}