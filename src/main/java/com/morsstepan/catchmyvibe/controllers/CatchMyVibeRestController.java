package com.morsstepan.catchmyvibe.controllers;

import com.morsstepan.catchmyvibe.TrackSpotify;
import com.morsstepan.catchmyvibe.models.HomeModelView;
import com.morsstepan.catchmyvibe.models.PlaylistModelView;
import com.morsstepan.catchmyvibe.service.SpotifyService;
import com.morsstepan.catchmyvibe.config.SpotifyConfigConstants;
import com.morsstepan.catchmyvibe.config.SpotifyOAuth2User;
import com.wrapper.spotify.model_objects.specification.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Component
public class CatchMyVibeRestController {
    @Autowired
    private SpotifyService spotifyService;

    private static String AUTH_BASE_URI = "oauth2/authorization/" + SpotifyConfigConstants.REGISTRATION_ID;

    @GetMapping("/auth")
    public String login(Model model, Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/home";
        }
        model.addAttribute("authorizationUrl", AUTH_BASE_URI);
        return "index";
    }

    @PostMapping("/generate")
    public ModelAndView generateSubmit(@ModelAttribute TrackSpotify track) {
        Playlist playlist = spotifyService.processTrackAndReturnPlaylist(track);
        return new ModelAndView("result", "playlist", playlist)
                .addObject("playlistLink", playlist.getExternalUrls().get("spotify"));
    }

    @GetMapping("/home")
    public ModelAndView home(Authentication auth) throws Exception {
        SpotifyOAuth2User user = new SpotifyOAuth2User((DefaultOAuth2User) auth.getPrincipal());
        return new HomeModelView()
                .setUsername(user.getName())
                .setTrack(new TrackSpotify());
    }


}
