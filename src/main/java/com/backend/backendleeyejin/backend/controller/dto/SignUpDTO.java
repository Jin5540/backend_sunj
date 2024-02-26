package com.backend.backendleeyejin.backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SignUpDTO {

    @NotBlank(message = "userId를 입력하십시오")
    @Pattern(regexp = "^[a-z0-9]*$", message = "userId는 영어 소문자와 숫자만 사용하여 입력해야 합니다")
    @Size(min=4,max=12, message = "userId는 4~12자 사이로 입력해주십시오")
    private String userId;

    @NotBlank(message = "password를 입력하십시오")
    @Pattern(regexp = "^[0-9a-z]*$", message = "password는 영어 소문자와 숫자만 사용하여 입력해야 합니다")
    @Size(min=4,max=15, message = "password는 4~15자 사이로 입력해주십시오")
    private String password;

    @NotBlank(message = "name을 입력하십시오")
    @Pattern(regexp = "^[가-힣]*$", message = "name은 한글만 사용하여 입력해야 합니다")
    @Size(min=3,max=10, message = "name는 3~10자 사이로 입력해주십시오")
    private String name;

    @NotBlank(message = "regNo를 입력하십시오")
    @Pattern(regexp = "\\d{6}\\-[1-4]\\d{6}", message = "올바른 주민등록번호를 입력해주십시오")
    private String regNo;
}
