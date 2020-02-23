package com.morsstepan.catchmyvibe.config;

import com.wrapper.spotify.SpotifyApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@PropertySource("classpath:secrets.properties")
public class AppConfigSpotify {
    private static final String CLIENT_ID_HEROKU_ENV = System.getenv("api.spotify.clientId");
    private static final String SECRET_HEROKU_ENV = System.getenv("api.spotify.secret");


    //    @Value(${api.spotify.clientId})
    String clientId = CLIENT_ID_HEROKU_ENV;

    //    @Value(${api.spotify.secret})
    String secret = SECRET_HEROKU_ENV;

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SpotifyApi spotifyApi(OAuth2AuthorizedClientService clientService) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(SpotifyConfigConstants.REGISTRATION_ID, authentication.getName());
        return new SpotifyApi.Builder()
                .setClientId(this.clientId)
                .setClientSecret(this.secret)
                .setAccessToken(client.getAccessToken().getTokenValue())
                .build();
    }

}