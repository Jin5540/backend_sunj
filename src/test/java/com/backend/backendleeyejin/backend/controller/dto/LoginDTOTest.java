package com.backend.backendleeyejin.backend.controller.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LoginDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @AfterAll
    public static void close() {
        factory.close();
    }

    @Test
    @DisplayName("userId 빈문자열 전송시 에러 발생")
    void blank_validation_test() {

        // given
        LoginDTO loginDTO = new LoginDTO("", "123456");
        // when
        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO); // 유효하지 않은 경우 violations 값을 가지고 있다.
        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("userId를 입력하십시오");
                });
    }

    @Test
    @DisplayName("유효성검사 성공")
    void success_validation_test() {

        // given
        LoginDTO loginDTO = new LoginDTO("hong12", "123456");
        // when
        Set<ConstraintViolation<LoginDTO>> violations = validator.validate(loginDTO); // 유효하지 않은 경우 violations 값을 가지고 있다.
        // then
        assertThat(violations).isEmpty();
    }

}