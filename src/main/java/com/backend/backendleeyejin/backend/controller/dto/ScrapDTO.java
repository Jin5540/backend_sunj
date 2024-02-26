package com.backend.backendleeyejin.backend.controller.dto;

import com.backend.backendleeyejin.backend.repository.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScrapDTO {

    //급여
    private String pay;
    //산출세액
    private String cat;
    //퇴직연금납입금액
    private String rd;
    //보험료납입금액
    private String ipd;
    //의료비납입금액
    private String med;
    //교육비납입금액
    private String ed;
    //기부금납입금액
    private String dd;
    @JsonIgnore
    private Member member;
    private String message;
}
