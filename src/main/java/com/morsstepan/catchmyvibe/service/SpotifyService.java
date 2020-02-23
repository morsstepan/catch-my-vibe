package com.morsstepan.catchmyvibe.service;

import com.morsstepan.catchmyvibe.TrackSpotify;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Service
public class SpotifyService {

    @Autowired
    private SpotifyApi spotify;

    public Playlist createPlaylist(Recommendations recommendations) {
        try {
            final Playlist playlist = spotify.createPlaylist(spotify.getCurrentUsersProfile().build().execute().getId(),
                    "CatchMyVibe Playlist | " + LocalDateTime.now()).build().execute();
            spotify.changePlaylistsDetails(playlist.getId()).description("Created with <3 by CatchMyVibe").build().execute();
            spotify.addTracksToPlaylist(playlist.getId(),
                    Arrays.stream(Arrays.stream(recommendations.getTracks()).map(TrackSimplified::getUri).toArray()).toArray(String[]::new))
            .build()
            .execute();

            return playlist;
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
           throw new RuntimeException();
        }
    }

    public Playlist processTrackAndReturnPlaylist(TrackSpotify track) {
        Paging<Track> trackPaging = searchTrack(track);
        track.setId(trackPaging.getItems()[0].getId());
        try {
            Recommendations recommendations = spotify.getRecommendations().seed_tracks(track.getId()).limit(50).build().execute();
            return createPlaylist(recommendations);
        } catch (IOException | SpotifyWebApiException e) {
            throw new RuntimeException();
        }
    }


    public Paging<Track> searchTrack(TrackSpotify track) {
        SearchTracksRequest searchTracksRequest = spotify.searchTracks(track.getArtist() + " " + track.getSong()).build();
        try {
            return searchTracksRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException();
        }
    }
}