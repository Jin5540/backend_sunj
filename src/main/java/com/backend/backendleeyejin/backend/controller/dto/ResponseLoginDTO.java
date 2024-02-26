package com.backend.backendleeyejin.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseLoginDTO {
    private String accessToken;
    private String message;
}
