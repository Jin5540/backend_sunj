package com.backend.backendleeyejin.backend.repository.entity;

import com.backend.backendleeyejin.backend.controller.dto.ScrapDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long index;
    //급여
    private String pay;
    //결정세액
    //private String dta;
    //산출세액
    private String cat;
    //근로소득세액공제금액
    //private String eitc;
    //특별세액공제금액
    //private String stc;
    //표준세액공제금액
    //private String sd;
    //퇴직연금세액공제금액
    //private String rpd;
    //퇴직연금납입금액
    private String rd;

    //보험료공제금액
    //private String ipdc;
    //보험료납입금액
    private String ipd;
    //의료비공제금액
    //private String medc;
    //의료비납입금액
    private String med;
    //교육비공제금액
    //private String edc;
    //교육비납입금액
    private String ed;
    //기부금공제금액
    //private String ddc;
    //기부금납입금액
    private String dd;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_index", referencedColumnName = "index")
    private Member member;

    @CreationTimestamp
    private LocalDateTime createDate;

    public static Data toData(ScrapDTO scrapDTO){
        Data data = new Data();
        data.setPay(scrapDTO.getPay());
        data.setEd(scrapDTO.getEd());
        data.setDd(scrapDTO.getDd());
        data.setMed(scrapDTO.getMed());
        data.setIpd(scrapDTO.getIpd());
        data.setCat(scrapDTO.getCat());
        data.setRd(scrapDTO.getRd());
        data.setMember(scrapDTO.getMember());
        return data;
    }

}
