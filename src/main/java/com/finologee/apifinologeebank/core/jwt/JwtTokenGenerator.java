package com.finologee.apifinologeebank.core.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenGenerator {
    public static final String SECRET_KEY = "9faa372517ac1d389758d3750fc07acf00f542277f26fec1ce4593e93f64e338";
    /*
        public String generateAccessToken2(Authentication authentication) {

            log.info("[JwtTokenGenerator:generateAccessToken] Token Creation Started for:{}", authentication.getName());
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("Finologee")
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                    .subject(authentication.getName())
                    .build();

            return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        }*/
    private final JwtEncoder encoder;

    public String generateAccessToken(Authentication authentication) {

        log.info("[JwtTokenGenerator:generateAccessToken] Token Creation Started for:{}", authentication.getName());

        return Jwts
                .builder()
                .subject(authentication.getName())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                                          .issuer("self")
                                          .issuedAt(now)
                                          .expiresAt(now.plus(1, ChronoUnit.HOURS))
                                          .subject(authentication.getName())
                                          .build();
        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return this.encoder.encode(encoderParameters).getTokenValue();
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

