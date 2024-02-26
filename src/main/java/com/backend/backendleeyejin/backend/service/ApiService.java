package com.backend.backendleeyejin.backend.service;

import com.backend.backendleeyejin.backend.controller.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;

import java.util.Map;

public interface ApiService {
    ResponseSignUpDTO signup(SignUpDTO signupdto);
    Map<String, String> validateHandling(Errors errors);
    ResponseLoginDTO login(LoginDTO loginDTO);
    ScrapDTO scrap(Authentication authentication) throws JsonProcessingException;
    RefundDTO refund(Authentication authentication);

}
