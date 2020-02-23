package com.morsstepan.catchmyvibe.service;

import com.morsstepan.catchmyvibe.TrackSpotify;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.miscellaneous.AudioAnalysis;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SpotifyService {

    @Autowired
    private SpotifyApi spotify;

    public List<PlaylistSimplified> getSimplifiedPlaylists() throws IOException {
        try {
            int offset = 0;
            int limit = 50;
            Paging<PlaylistSimplified> paging;List<PlaylistSimplified> playlists = new ArrayList<>();

            do {
				paging = this.spotify.getListOfCurrentUsersPlaylists().limit(limit).offset(offset).build().execute();
                playlists.addAll(Arrays.asList(paging.getItems()));
                offset += limit;
            } while (paging.getNext() != null);

            return playlists;
        } catch (SpotifyWebApiException e) {
            throw new RuntimeException(e);
        }
    }


    public List<PlaylistTrack> getPlaylistTracks(String playlistId) throws IOException {
        try {
            int offset = 0;
            int limit = 100;
            Paging<PlaylistTrack> paging;
            List<PlaylistTrack> tracks = new ArrayList<>();

            do {
                paging = this.spotify.getPlaylistsTracks(playlistId).fields("next,items(is_local,track(name, artists, is_playable))")
                        .limit(limit).offset(offset)
                        .market(CountryCode.CA)
                        .build().execute();
                tracks.addAll(Arrays.asList(paging.getItems()));
                offset += limit;
            } while (paging.getNext() != null);

            return tracks;
        } catch (SpotifyWebApiException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AudioFeatures> getAudioFeatures(List<String> ids) throws IOException {
        try {
            List<AudioFeatures> features = new ArrayList<>();

            while (features.size() < ids.size()) {
                int from = features.size();
                List<String > subList = ids.subList(from, Math.min(ids.size(), from + 100));
                String[] idsArr = new String[subList.size()];
                subList.toArray(idsArr);
                AudioFeatures[] subFeatures = this.spotify.getAudioFeaturesForSeveralTracks(idsArr).build().execute();
                features.addAll(Arrays.asList(subFeatures));
            }

            return features;
        } catch (SpotifyWebApiException e) {
            throw new RuntimeException(e);
        }
    }


    public Playlist getPlaylist(String id) throws IOException {
        try {
            return this.spotify.getPlaylist(id).build().execute();
        } catch (SpotifyWebApiException e) {
            throw new RuntimeException(e);
        }

    }

    public Playlist createPlaylist() {
        try {
//            spotify.getCurrentUsersProfile().build().execute().getId()
            final Playlist playlist = spotify.createPlaylist(spotify.getCurrentUsersProfile().build().execute().getId(),
                    "CatchMyVibePlaylist").build().execute();
            System.out.println("Name: " + playlist.getName());
            return playlist;
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
           throw new RuntimeException();
        }
    }

    public Recommendations processTrackAndGetRecommendations(TrackSpotify track) {
        Paging<Track> trackPaging = searchTrack(track);
        track.setId(trackPaging.getItems()[0].getId());

        try {
            final AudioAnalysis audioAnalysis = spotify.getAudioAnalysisForTrack(track.getId()).build().execute();
            return spotify.getRecommendations().seed_tracks(track.getId()).build().execute();
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