package com.backend.backendleeyejin.backend.configuration;

import com.backend.backendleeyejin.backend.provider.JwtProvider;
import com.backend.backendleeyejin.backend.repository.MemberRepository;
import com.backend.backendleeyejin.backend.repository.entity.Member;
import com.backend.backendleeyejin.backend.service.ApiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final ApiService apiService;
    private final MemberRepository memberRepository;

    @Value("${jwt.secret-key}")
    private final String secretKey;

    @Value("${jwt.token-type}")
    private final String tokenType;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final JwtProvider jwtProvider = new JwtProvider();

        log.info("JwtFilter _____________________________");

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String error = "";

        log.info("authorization : {}",authorization);

        if (authorization == null || !authorization.startsWith(tokenType)){
            error = "존재하지 않는 accessToken입니다. 다시 확인하여주십시오.";
            log.error(error);
            filterChain.doFilter(request,response);
            return;
        }

        String accessToken = authorization.split(" ")[1];

        if(jwtProvider.isExpired(accessToken, secretKey)){
            error="accessToken 만료되었습니다. 다시 확인하여 주십시오.";
            log.error(error);
            filterChain.doFilter(request, response);
            return;
        }
        String userId = jwtProvider.getUserId(accessToken, secretKey);
        if(userId.isEmpty()){
            error="user가 존재하지 않습니다. 다시 확인하여 주십시오.";
            log.error(error);
            return;
        }
        log.info("userId : {}", userId);
        Optional<Member> optionalMember = memberRepository.findByUserId(userId);
        if(optionalMember.isEmpty()){
            error="user 정보가 존재하지 않습니다. 다시 확인하여주십시오.";
            log.error(error);
            return ;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId,null, List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
