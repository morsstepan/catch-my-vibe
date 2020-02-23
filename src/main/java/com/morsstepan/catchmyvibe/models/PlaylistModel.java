package com.morsstepan.catchmyvibe.models;

import org.springframework.ui.ModelMap;

public class PlaylistModel extends ModelMap {

    public PlaylistModel setId(String id) {
        addAttribute("id", id);
        return this;
    }

    public PlaylistModel setName(String name) {
        addAttribute("name", name);
        return this;
    }

    public PlaylistModel setImage(String image) {
        addAttribute("image", image);
        return this;
    }

}