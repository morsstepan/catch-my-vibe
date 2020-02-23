package com.morsstepan.catchmyvibe.config;

import com.google.gson.Gson;
import com.wrapper.spotify.model_objects.specification.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class SpotifyOAuth2User implements OAuth2User {

    private final User user;
    private final DefaultOAuth2User defaultUser;

    public SpotifyOAuth2User(DefaultOAuth2User defaultUser) {
        this.defaultUser = defaultUser;
        Gson gson = new Gson();
        String rawJson = gson.toJson(defaultUser.getAttributes());
        this.user = new User.JsonUtil().createModelObject(rawJson);
    }

    public User getUser() {
        return this.user;
    }

	@Override
	public String getName() {
        return this.defaultUser.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.defaultUser.getAuthorities();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.defaultUser.getAttributes();
	}

}