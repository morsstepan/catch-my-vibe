package com.morsstepan.catchmyvibe.models;

import org.springframework.ui.ModelMap;

import java.util.List;

public class TrackModel extends ModelMap {

    public TrackModel setName(String name) {
        addAttribute("name", name);
        return this;
    }

    public TrackModel setArtist(ArtistModel artist) {
        addAttribute("artist", artist);
        return this;
    }

    public TrackModel setId(String id) {
        addAttribute("id", id);
        return this;
    }

}