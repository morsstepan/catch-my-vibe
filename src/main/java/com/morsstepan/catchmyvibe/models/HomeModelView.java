package com.morsstepan.catchmyvibe.models;

import com.morsstepan.catchmyvibe.TrackSpotify;
import org.springframework.web.servlet.ModelAndView;

public class HomeModelView extends ModelAndView {

    public HomeModelView() {
        super("home");
    }

    public HomeModelView setUsername(String username) {
        addObject("username", username);
        return this;
    }

    public HomeModelView setTrack(TrackSpotify trackSpotify) {
        addObject("track", trackSpotify);
        return this;
    }

//    public HomeModelView setPlaylists(List<PlaylistModel> playlists) {
//        addObject("playlists", playlists);
//        return this;
//    }

}