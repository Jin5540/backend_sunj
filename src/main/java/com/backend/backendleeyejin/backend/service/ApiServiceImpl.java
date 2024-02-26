package com.backend.backendleeyejin.backend.service;

import com.backend.backendleeyejin.backend.controller.dto.*;
import com.backend.backendleeyejin.backend.provider.EncoderProvider;
import com.backend.backendleeyejin.backend.provider.JsoupProvider;
import com.backend.backendleeyejin.backend.provider.JwtProvider;
import com.backend.backendleeyejin.backend.provider.RefundProvider;
import com.backend.backendleeyejin.backend.repository.DataRepository;
import com.backend.backendleeyejin.backend.repository.MemberRepository;
import com.backend.backendleeyejin.backend.repository.TokenRepository;
import com.backend.backendleeyejin.backend.repository.entity.Data;
import com.backend.backendleeyejin.backend.repository.entity.Member;
import com.backend.backendleeyejin.backend.repository.entity.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import com.backend.backendleeyejin.backend.configuration.EncryptionConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final DataRepository dataRepository;
    private final EncryptionConfig encryptionConfig;
    private final JwtProvider jwtProvider;
    private final JsoupProvider jsoupProvider;
    private final EncoderProvider encoderProvider;
    private final RefundProvider refundProvider;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expire-length}")
    private int tokenValidTime;


    @Override
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Override
    public ResponseSignUpDTO signup(SignUpDTO signupdto){

        ResponseSignUpDTO responseSignUpDTO = new ResponseSignUpDTO();
        ArrayList<String> response = new ArrayList<String>();
        boolean checkUserId = memberRepository.existsByUserId(signupdto.getUserId());
        boolean checkRegNo = memberRepository.existsByRegNo(signupdto.getRegNo());

        if (checkUserId){
            response.add("중복되는 userId입니다. 다시 입력해주십시오");
            responseSignUpDTO.setMessageList(response);
            return responseSignUpDTO;
        }
        else if(checkRegNo){
            response.add("중복되는 regNo입니다. 다시 입력해주십시오");
            responseSignUpDTO.setMessageList(response);
            return responseSignUpDTO;
        }else{
            signupdto.setPassword(encryptionConfig.encoder().encode(signupdto.getPassword()));
            signupdto.setRegNo(encoderProvider.encrypt(signupdto.getRegNo()));

            Member member = Member.toMember(signupdto);
            memberRepository.save(member);

            response.add("회원가입이 완료되었습니다");
            responseSignUpDTO.setMessageList(response);
            return responseSignUpDTO;
        }
    }

    @Override
    public ResponseLoginDTO login(LoginDTO loginDTO) {
        ResponseLoginDTO responseLoginDTO = new ResponseLoginDTO();
        Optional<Member> optionalMember = memberRepository.findByUserId(loginDTO.getUserId());
        
        if(optionalMember.isEmpty()){
            responseLoginDTO.setMessage("userId가 없습니다. 다시 확인해주십시오");
            return responseLoginDTO;

        }else{
            Member member =optionalMember.get();
            boolean checkPassword = encryptionConfig.encoder().matches(loginDTO.getPassword(),member.getPassword());
            if(!checkPassword){
                responseLoginDTO.setMessage("password가 일치하지 않습니다. 다시 확인해주십시오");
                return responseLoginDTO;
            }else {
                responseLoginDTO.setAccessToken(jwtProvider.createToken(loginDTO.getUserId(),secretKey, tokenValidTime));
                Token token = new Token();
                token.setAccessToken(responseLoginDTO.getAccessToken());
                token.setMember(member);

                tokenRepository.save(token);

                return responseLoginDTO;
            }
        }
    }

    @Override
    public ScrapDTO scrap(Authentication authentication) throws JsonProcessingException {
        ScrapDTO scrapDTO = new ScrapDTO();
        RequestDTO requestDTO = new RequestDTO();
        ObjectMapper mapper = new ObjectMapper();

        String userId = authentication.getName();
        Optional<Member> optionalMember = memberRepository.findByUserId(userId);
        if(optionalMember.isEmpty()){
            scrapDTO.setMessage("user 정보가 존재하지 않습니다. 다시 확인하여 주십시오");
            return scrapDTO;
        }else {
            
            Member member =optionalMember.get();
            if(dataRepository.existsByIndex(member.getIndex())){
                scrapDTO.setMessage("같은 user의 정보를 불러올 수 없습니다");
                return scrapDTO;
            }
            requestDTO.setName(member.getName());
            requestDTO.setRegNo(encoderProvider.decrypt(member.getRegNo()));

            String jsonString = mapper.writeValueAsString(requestDTO);

            return null;
            //return jsoupProvider.process(jsonString, member);
        }
    }

    @Override
    public RefundDTO refund(Authentication authentication) {
        Optional<Member> optionalMember = memberRepository.findByUserId(authentication.getName());

        if(optionalMember.isEmpty()){
            return null;
        }else {
            Member member =optionalMember.get();
            Optional<Data> optionalData = dataRepository.findByIndex(member.getIndex());
            if(optionalData.isEmpty()){
                return null;
            }else{
                Data data = optionalData.get();
                return refundProvider.refund(data, member);
            }
        }
    }

}
