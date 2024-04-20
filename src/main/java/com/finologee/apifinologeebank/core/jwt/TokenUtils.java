package com.finologee.apifinologeebank.core.jwt;

import com.finologee.apifinologeebank.core.config.UserConfig;
import com.finologee.apifinologeebank.core.exception.NotFoundException;
import com.finologee.apifinologeebank.core.repository.TokenRepository;
import com.finologee.apifinologeebank.core.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TokenUtils {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public String extractUsername(Jwt token) {
        return token.getSubject();
    }


    public boolean isTokenValid(Jwt jwtToken, UserDetails userDetails){
        final String userName = extractUsername(jwtToken);
        boolean isTokenExpired = getIfTokenIsExpired(jwtToken);
        boolean validToken = tokenRepository
                .findByToken(jwtToken.getTokenValue())
                .map(t -> !t.isLoggedOut())
                .orElse(false);
        boolean isTokenUserSameAsDatabase = userName.equals(userDetails.getUsername());
        return !isTokenExpired  && isTokenUserSameAsDatabase && validToken;

    }

    private boolean getIfTokenIsExpired(Jwt jwtToken) {
        return Objects.requireNonNull(jwtToken.getExpiresAt()).isBefore(Instant.now());
    }
    public UserDetails userDetails(String username){
        return userRepository
                .findByUsername(username)
                .map(UserConfig::new)
                .orElseThrow(()-> new NotFoundException("User does not exist"));
    }
}
