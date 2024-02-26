package com.backend.backendleeyejin.backend.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.binary.Base64;

@RequiredArgsConstructor
@Component
public class EncoderProvider {

    public String encrypt(String regNo) {
        byte[] regNoBytes = regNo.getBytes();
        byte[] encodeByte = Base64.encodeBase64(regNoBytes);

        return new String(encodeByte);
    }
    public String decrypt(String regNo) {
        byte[] regNoBytes = regNo.getBytes();
        byte[] decodeByte = Base64.decodeBase64(regNoBytes);

        return new String(decodeByte);
    }

}
