package com.morsstepan.catchmyvibe.controllers;

import com.morsstepan.catchmyvibe.models.HomeModelView;
import com.morsstepan.catchmyvibe.PlaylistMapper;
import com.morsstepan.catchmyvibe.models.PlaylistModel;
import com.morsstepan.catchmyvibe.service.SpotifyService;
import com.morsstepan.catchmyvibe.config.SpotifyConfigConstants;
import com.morsstepan.catchmyvibe.config.SpotifyOAuth2User;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CatchMyVibeRestController {
    @Autowired
    private SpotifyService spotifyService;
//
    @GetMapping("/hello")
    public String hello(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
                        Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    private static String AUTH_BASE_URI = "oauth2/authorization/" + SpotifyConfigConstants.REGISTRATION_ID;

    @GetMapping("/auth")
    public String login(Model model, Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/home";
        }
        model.addAttribute("authorizationUrl", AUTH_BASE_URI);
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView home(Authentication auth) throws Exception {
//        SpotifyOAuth2User user = (SpotifyOAuth2User) auth.getPrincipal();

        SpotifyOAuth2User user = new SpotifyOAuth2User((DefaultOAuth2User) auth.getPrincipal());
        List<PlaylistSimplified> playlists = this.spotifyService.getSimplifiedPlaylists();
        List<PlaylistModel> playlistDetails = playlists.stream()
                .map(PlaylistMapper::map)
                .collect(Collectors.toList());

        return new HomeModelView()
                .setUsername(user.getName())
                .setPlaylists(playlistDetails);
    }

}
