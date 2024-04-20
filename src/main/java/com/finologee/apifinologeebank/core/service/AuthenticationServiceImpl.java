package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.dto.AuthenticationResponseDto;
import com.finologee.apifinologeebank.core.jwt.JwtTokenGenerator;
import com.finologee.apifinologeebank.core.model.Token;
import com.finologee.apifinologeebank.core.model.User;
import com.finologee.apifinologeebank.core.repository.TokenRepository;
import com.finologee.apifinologeebank.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    public AuthenticationResponseDto getJwtTokensAfterAuthentication(Authentication authentication) {
        try
        {
            var user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(()->{
                        log.error("[AuthService:userSignInAuth] User :{} not found",authentication.getName());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND,"USER NOT FOUND ");});


            String accessToken = jwtTokenGenerator.generateToken(authentication);

            saveUserToken(accessToken, user);

            log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated",user.getUsername());
            return  AuthenticationResponseDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15 * 60)
                    .userName(user.getUsername())
                    .tokenType(OAuth2AccessToken.TokenType.BEARER)
                    .build();


        }catch (Exception e){
            log.error("[AuthenticationService:userSignInAuth]Exception while authenticating the user due to :"+e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please Try Again");
        }
    }
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
