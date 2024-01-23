package kr.lesh.login_example.controller;


import io.jsonwebtoken.JwtBuilder;
import kr.lesh.login_example.dto.UserDto;
import kr.lesh.login_example.entity.Auth;
import kr.lesh.login_example.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class AuthController {

    JwtBuilder jwtBuilder;
    SecretKey secretKey;
    AuthRepository authRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto dto) {
        if(!dto.getUserid().equals("lesh") || !dto.getPassword().equals("1234")) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        Date expiredAt = Date.from(Instant.now().plus(Duration.ofDays(1L)));
        String jwtId = UUID.randomUUID().toString();

        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userid", dto.getUserid());

        String token = jwtBuilder.setHeader(headerMap)
                .setClaims(claims)
                .setExpiration(expiredAt)
                .setId(jwtId)
                .signWith(secretKey)
                .compact();

        authRepository.save(Auth.create(token));
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/svc")
    public ResponseEntity<String> svc(@RequestHeader("Authorization") String token) {
        if(!authRepository.existsByToken(token.split(" ")[1])) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Login된 사용자입니다.", HttpStatus.OK);
    }
}
