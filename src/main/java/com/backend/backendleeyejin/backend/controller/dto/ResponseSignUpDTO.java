package com.backend.backendleeyejin.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseSignUpDTO {
    private ArrayList<String> messageList;
}
