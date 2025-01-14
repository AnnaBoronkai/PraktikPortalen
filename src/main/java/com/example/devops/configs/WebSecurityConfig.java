package com.example.devops.configs;

import com.example.devops.services.implementations.UserServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserServiceImplementation userServiceImplementation;
    private final ClientRegistrationRepository clientRegistrationRepository;

    public WebSecurityConfig(UserServiceImplementation userServiceImplementation
    , ClientRegistrationRepository clientRegistrationRepository) {
        this.userServiceImplementation = userServiceImplementation;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/css/**", "/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserRequest -> {
                                    OidcUserService delegate = new OidcUserService();
                                    OidcUser oidcUser = delegate.loadUser(oidcUserRequest);
                                    userServiceImplementation.loadUser(oidcUser);
                                    return oidcUser;
                                })
                        )
                        .authorizationEndpoint(authorization -> authorization
                                .authorizationRequestResolver(requestResolver())
                        )
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .permitAll()
                ).build();
    };

    private OAuth2AuthorizationRequestResolver requestResolver() {
        DefaultOAuth2AuthorizationRequestResolver resolver = new DefaultOAuth2AuthorizationRequestResolver(
               this.clientRegistrationRepository, "/oauth2/authorization"
        );
        resolver.setAuthorizationRequestCustomizer(request -> request
                .attributes(attributes -> attributes
                        .remove(OidcParameterNames.NONCE))
                .parameters(params -> params
                        .remove(OidcParameterNames.NONCE))
        );
        return resolver;
    }
}
