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
import static org.assertj.core.api.Assertions.offset;

//import static org.junit.jupiter.api.Assertions.*;

class SignUpDTOTest {

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
        SignUpDTO signUpDTO = new SignUpDTO("", "123456","홍길동","860824-1655068");
        // when
        Set<ConstraintViolation<SignUpDTO>> violations = validator.validate(signUpDTO); // 유효하지 않은 경우 violations 값을 가지고 있다.
        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(error -> {
                    assertThat(error.getMessage()).containsAnyOf("userId를 입력하십시오","userId는 4~12자 사이로 입력해주십시오");
                });
    }

    @Test
    @DisplayName("userId 형식 오류 전송시 에러 발생")
    void userId_validation_test() {

        // given
        SignUpDTO signUpDTO = new SignUpDTO("hong@@@@@", "123456","홍길동","860824-1655068");
        // when
        Set<ConstraintViolation<SignUpDTO>> violations = validator.validate(signUpDTO); // 유효하지 않은 경우 violations 값을 가지고 있다.
        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("userId는 영어 소문자와 숫자만 사용하여 입력해야 합니다");
                });
    }

    @Test
    @DisplayName("userId 크기 오류 전송시 에러 발생")
    void size_validation_test() {

        // given
        SignUpDTO signUpDTO = new SignUpDTO("hong1111111111111111", "123456","홍길동","860824-1655068");
        // when
        Set<ConstraintViolation<SignUpDTO>> violations = validator.validate(signUpDTO); // 유효하지 않은 경우 violations 값을 가지고 있다.
        // then
        assertThat(violations).isNotEmpty();
        violations
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("userId는 4~12자 사이로 입력해주십시오");
                });
    }

    @Test
    @DisplayName("유효성검사 성공")
    void success_validation_test() {

        // given
        SignUpDTO signUpDTO = new SignUpDTO("hong12", "123456","홍길동","860824-1655068");
        // when
        Set<ConstraintViolation<SignUpDTO>> violations = validator.validate(signUpDTO); // 유효하지 않은 경우 violations 값을 가지고 있다.
        // then
        assertThat(violations).isEmpty();
    }

}