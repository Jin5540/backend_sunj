package com.backend.backendleeyejin.backend.configuration;

import com.backend.backendleeyejin.backend.repository.MemberRepository;
import com.backend.backendleeyejin.backend.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApiService apiService;
    private final MemberRepository memberRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-type}")
    private String tokenType;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring().requestMatchers("/h2-console/**","/szs/signup","/szs/login")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity Http)  throws Exception{
        HttpSecurity httpSecurity = Http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtFilter(apiService,memberRepository,secretKey,tokenType), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/h2-console/**","szs/login","szs/signup").permitAll()
                                .anyRequest().authenticated()
                );

        return httpSecurity.build();
    }
}
