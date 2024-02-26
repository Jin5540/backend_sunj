package com.backend.backendleeyejin.backend.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

import com.backend.backendleeyejin.backend.controller.dto.LoginDTO;
import com.backend.backendleeyejin.backend.controller.dto.SignUpDTO;
import com.backend.backendleeyejin.backend.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

    @Mock
    private ApiService apiService;
    @InjectMocks
    private ApiController apiController;
    private MockMvc mvc;

    private ObjectMapper mapper;

    private Authentication authentication;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    void signup () throws Exception{
        //given
        String userId = "hong12";
        String password = "123456";
        String name = "홍길동";
        String regNo = "860824-1655068";
        //when

        Gson gson = new Gson();

        String body = gson.toJson(
                SignUpDTO.builder().userId(userId).password(password).name(name).regNo(regNo).build()
        );
        //then
        mvc.perform(post("/backend/signup")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }


    @Test
    void login() throws Exception{
        //given
        String userId = "hong12";
        String password = "123456";

        Gson gson = new Gson();

        String body = gson.toJson(
                LoginDTO.builder().userId(userId).password(password).build()
        );

        mvc.perform(post("/backend/login")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void scrap() throws Exception{
        mvc.perform(post("/backend/scrap").with(authentication(authentication))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    /*
    @Test
    void refund() throws Exception{
        mvc.perform(post("/backend/refund").with(authentication(authentication))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
     */


}