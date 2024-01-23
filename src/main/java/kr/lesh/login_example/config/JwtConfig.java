package kr.lesh.login_example.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {

    @Bean
    public SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    public JwtBuilder jwtBuilder() {
        return Jwts.builder();
    }
}
