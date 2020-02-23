package com.morsstepan.catchmyvibe;

import org.springframework.ui.ModelMap;

import java.util.List;

public class TrackModel extends ModelMap {

    public TrackModel setName(String name) {
        addAttribute("name", name);
        return this;
    }

    public TrackModel setArtists(List<String> artists) {
        addAttribute("artists", artists);
        return this;
    }

    public TrackModel setIsLocal(boolean isLocal) {
        addAttribute("isLocal", isLocal);
        return this;
    }

    public TrackModel setIsPlayable(boolean isPlayable) {
        addAttribute("isPlayable", isPlayable);
        return this;
    }

}