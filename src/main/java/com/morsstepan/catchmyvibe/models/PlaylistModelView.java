package com.morsstepan.catchmyvibe.models;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class PlaylistModelView extends ModelAndView {

    public PlaylistModelView() {
        super("playlist");
    }

    public PlaylistModelView setPlaylist(PlaylistModel playlist) {
        addObject("playlist", playlist);
        return this;
    }

    public PlaylistModelView setTracks(List<TrackModel> tracks) {
        addObject("tracks", tracks);
        return this;
    }

}