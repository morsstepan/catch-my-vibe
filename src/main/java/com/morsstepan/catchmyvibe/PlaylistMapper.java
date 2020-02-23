package com.morsstepan.catchmyvibe;

import com.morsstepan.catchmyvibe.models.PlaylistModel;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;

public class PlaylistMapper {


    public static PlaylistModel map(PlaylistSimplified playlist) {
        String image = playlist.getImages().length > 0
                ? playlist.getImages()[0].getUrl() : null;
        return new PlaylistModel()
            .setId(playlist.getId())
            .setName(playlist.getName())
            .setImage(image);
    }


    public static PlaylistModel map(Playlist playlist) {
        String image = playlist.getImages().length > 0
                ? playlist.getImages()[0].getUrl() : null;
        return new PlaylistModel()
            .setId(playlist.getId())
            .setName(playlist.getName())
            .setImage(image);
    }

}