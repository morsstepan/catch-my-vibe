package com.morsstepan.catchmyvibe.config;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SpotifyConfigConstants {

    private static final String AUTH_BASE_URL = "https://accounts.spotify.com";

    public static final String AUTHORIZATION_URL = AUTH_BASE_URL + "/authorize";
    public static final String TOKEN_URL = AUTH_BASE_URL + "/api/token";

    private static final String API_BASE_URL = "https://api.spotify.com/v1";
    public static final String USER_INFO = API_BASE_URL + "/me";

    public static final String REGISTRATION_ID = "spotify";

    public enum Scopes {

        PLAYLIST_READ_PRIVATE("playlist-read-private"),
        PLAYLIST_MODIFY_PUBLIC("playlist-modify-public"),
        PLAYLIST_MODIFY_PRIVATE("playlist-modify-private"),
        PLAYLIST_READ_COLLABORATIVE("playlist-read-collaborative");

        private final String scope;

        private Scopes(String scope) {
            this.scope = scope;
        }

        @Override
        public String toString() {
            return this.scope;
        }
        
        public static Collection<String> getAll() {
            return Stream.of(values()).map(Object::toString).collect(Collectors.toList());
        }

    }


    private SpotifyConfigConstants() {}

}