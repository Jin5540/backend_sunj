package com.backend.backendleeyejin.backend.provider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

//import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static net.bytebuddy.matcher.ElementMatchers.is;

@EnableConfigurationProperties
@SpringBootTest
class JwtTest {

    private final JwtProvider jwtProvider = new JwtProvider();

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expire-length}")
    private int tokenValidTime;

    @Test
    @DisplayName("토큰 생성 및 복호화 테스트")
    void tokenTest() {

        final String userId = "hong12";

        final String token = jwtProvider.createToken(userId,secretKey,tokenValidTime);
        String checkUserId = jwtProvider.getUserId(token,secretKey);
        assertThat(userId).isEqualTo(checkUserId);
    }
}