package com.example.project.base.config;

import com.example.project.admin.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/assets/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/trang-chu")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/dich-vu")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/i/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/tin-tuc")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/tin-tuc/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/dat-lich/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/forgot-password")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/reset-password")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/dashboard")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/dashboard/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/dashboard/edit-homepage")).hasAnyRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/dashboard/patients")).hasAnyRole("DOC", "ADMIN", "ASSIS", "RECEP", "NURSE")
                .requestMatchers(new AntPathRequestMatcher("/dashboard/departments")).hasAnyRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/dashboard/staffs")).hasAnyRole("ADMIN", "ASSIS")
                .requestMatchers(new AntPathRequestMatcher("/dashboard/services")).hasAnyRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/dashboard/categories")).hasAnyRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/dashboard/articles")).hasAnyRole("MEDIA", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/dashboard/invoice/")).permitAll()
                .anyRequest().permitAll()
        );
        http.formLogin(login -> login
                .loginPage("/login")
                .successForwardUrl("/dashboard")
                .permitAll());
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .successHandler(successHandler())
                .permitAll());
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/auth");
    }

}
