package com.morsstepan.catchmyvibe;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class HomeModelView extends ModelAndView {

    public HomeModelView() {
        super("home");
    }

    public HomeModelView setUsername(String username) {
        addObject("username", username);
        return this;
    }

    public HomeModelView setPlaylists(List<PlaylistModel> playlists) {
        addObject("playlists", playlists);
        return this;
    }

}