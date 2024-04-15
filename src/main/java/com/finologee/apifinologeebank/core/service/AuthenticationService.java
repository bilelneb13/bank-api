package com.finologee.apifinologeebank.core.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Object getJwtTokensAfterAuthentication(Authentication authentication);
}
