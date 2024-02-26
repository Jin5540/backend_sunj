package com.backend.backendleeyejin.backend.provider;

import com.backend.backendleeyejin.backend.controller.dto.ScrapDTO;
import com.backend.backendleeyejin.backend.repository.DataRepository;
import com.backend.backendleeyejin.backend.repository.entity.Data;
import com.backend.backendleeyejin.backend.repository.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Component
public class JsoupProvider {

    @Value("${szs.scrap.url}")
    private String scrapUrl;

    private final DataRepository dataRepository;

    public ScrapDTO process(String jsonString, Member member) {
        log.info("process _____________________________");

        ScrapDTO scrapDTO = new ScrapDTO();
        try {
            Document doc = Jsoup.connect(scrapUrl)
                    .ignoreContentType(true)
                    .requestBody(jsonString)
                    .timeout(20 * 1000)
                    .post();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());

            String status = (String) jsonObject.get("status");
            System.out.println("status ::: "+status);
            if(status.equals("success")){

                JSONObject data = (JSONObject) jsonObject
                        .get("data");
                JSONObject jsonList = (JSONObject) data.get("jsonList");
                JSONArray payList = (JSONArray) jsonList.get("급여");

                for(int i=0; i<payList.size(); i++){
                    JSONObject arr = (JSONObject) payList.get(0); //arr
                    scrapDTO.setPay((String) arr.get("총지급액"));
                }

                scrapDTO.setCat((String) jsonList.get("산출세액"));

                JSONArray array = (JSONArray) jsonList.get("소득공제");

                for(int i=0; i<array.size(); i++){
                    JSONObject idp = (JSONObject) array.get(0); //보험료
                    JSONObject ed = (JSONObject) array.get(1); //교육비
                    JSONObject dd = (JSONObject) array.get(2); //기부금
                    JSONObject med = (JSONObject) array.get(3); //의료비
                    JSONObject rd = (JSONObject) array.get(4); //퇴직연금

                    scrapDTO.setIpd((String) idp.get("금액"));
                    scrapDTO.setEd((String) ed.get("금액"));
                    scrapDTO.setDd((String) dd.get("금액"));
                    scrapDTO.setMed((String) med.get("금액"));
                    scrapDTO.setRd((String) rd.get("총납임금액"));
                }
                scrapDTO.setMember(member);
                Data dataEntity = Data.toData(scrapDTO);

                dataRepository.save(dataEntity);
                log.info("data를 저장하였습니다");


            }else if(status.equals("fail")){
                JSONObject errors = (JSONObject) jsonObject.get("errors");
                String message = (String) errors.get("message");
                log.error(message);

                scrapDTO.setMessage(message);
            }


        } catch (IOException | ParseException e) {
            scrapDTO.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return scrapDTO;
    }

}
