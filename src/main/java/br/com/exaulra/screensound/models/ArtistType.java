package br.com.exaulra.screensound.models;

public enum ArtistType {
    SOLO("solo"),
    DUPLA("dupla"),
    BANDA("banda");

    private String type;

    ArtistType(String type) {
        this.type = type;
    }

    public static ArtistType fromString(String text) {
        for (ArtistType artistType : ArtistType.values()) {
            if (artistType.type.equalsIgnoreCase(text)) {
                return artistType;
            }
        }
        throw new IllegalArgumentException("Tipo do artista incorreto " + text);
    }
}