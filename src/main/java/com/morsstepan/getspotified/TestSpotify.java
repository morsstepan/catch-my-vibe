package com.morsstepan.getspotified;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;

public class TestSpotify {
    public static void main(String[] args) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken("taHZ2SdB-bPA3FsK3D7ZN5npZS47cMy-IEySVEGttOhXmqaVAIo0ESvTCLjLBifhHOHOIuhFUKPW1WMDP7w6dj3MAZdWT8CLI2MkZaXbYLTeoDvXesf2eeiLYPBGdx8tIwQJKgV8XdnzH_DONk")
                .build();

        spotifyApi.clientCredentials();
    }
}
