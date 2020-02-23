package com.morsstepan.catchmyvibe.models;

import org.springframework.ui.ModelMap;

public class ArtistModel extends ModelMap {

    public ArtistModel setName(String name) {
        addAttribute("name", name);
        return this;
    }

    public ArtistModel setId(String id) {
        addAttribute("id", id);
        return this;
    }
}
