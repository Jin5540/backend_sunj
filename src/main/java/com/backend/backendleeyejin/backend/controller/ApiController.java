package com.backend.backendleeyejin.backend.controller;

import com.backend.backendleeyejin.backend.controller.dto.*;
import com.backend.backendleeyejin.backend.service.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/backend")
public class ApiController {

    private final ApiService apiService;
    @PostMapping("/signup")
    public ResponseSignUpDTO signup(@Valid @RequestBody SignUpDTO signupdto, Errors errors){

        log.info("signup ::: Controller _____________________________");

        final ResponseSignUpDTO responseSignUpDTO = new ResponseSignUpDTO();
        ArrayList<String> response = new ArrayList<String>();

        if (errors.hasErrors()){
            Map<String, String> validatorResult = apiService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                response.add(validatorResult.get(key));
            }
            responseSignUpDTO.setMessageList(response);
            return responseSignUpDTO;
        }
        return apiService.signup(signupdto);
    }

    @PostMapping("/login")
    public ResponseLoginDTO login(@Valid @RequestBody LoginDTO loginDTO, Errors errors){
        log.info("login ::: Controller _____________________________");

        final ResponseLoginDTO responseLoginDTO = new ResponseLoginDTO();

        if (errors.hasErrors()){
            Map<String, String> validatorResult = apiService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                responseLoginDTO.setMessage(validatorResult.get(key));
            }
            return responseLoginDTO;
        }
        return apiService.login(loginDTO);
    }


    @PostMapping("/scrap")
    public ScrapDTO scrap(Authentication authentication) throws JsonProcessingException {
        log.info("scrap ::: Controller _____________________________");
        return apiService.scrap(authentication);
    }

    /*
    @GetMapping("/refund")
    public RefundDTO refund(Authentication authentication){
        log.info("refund ::: Controller _____________________________");
        return apiService.refund(authentication);
    }
     */

}
