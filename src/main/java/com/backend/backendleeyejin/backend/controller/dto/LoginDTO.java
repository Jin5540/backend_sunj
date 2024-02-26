package com.backend.backendleeyejin.backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginDTO {

    @NotBlank(message = "userId를 입력하십시오")
    private String userId;

    @NotBlank(message = "password를 입력하십시오")
    private String password;

}
