package com.backend.backendleeyejin.backend.provider;

import com.backend.backendleeyejin.backend.controller.dto.RefundDTO;
import com.backend.backendleeyejin.backend.repository.entity.Data;
import com.backend.backendleeyejin.backend.repository.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Slf4j
@RequiredArgsConstructor
@Component
public class RefundProvider {

    public RefundDTO refund(Data data, Member member){
        RefundDTO refundDTO = new RefundDTO();

        DecimalFormat decFormat = new DecimalFormat("###,###");
        Double ca_t = Double.parseDouble(data.getCat().replaceAll(",",""));//산출세액
        Double eit_c= ca_t*0.55; //근로소득세액공제금액
        Double ipd_c=  Double.parseDouble(data.getIpd().replaceAll(",",""))*0.12; //보험료공제금액
        Double pay_ = Double.parseDouble(data.getPay().replaceAll(",",""))*0.03;
        Double med = Double.parseDouble(data.getMed().replaceAll(",",""));
        Double med_c = (med-pay_)*0.15; //의료비공제금액

        if(med_c<0){
            med_c = 0.0;
        }

        Double ed_c = Double.parseDouble(data.getEd().replaceAll(",",""))*0.15; //교육비공제금액

        Double dd_c = Double.parseDouble(data.getDd().replaceAll(",",""))*0.15; //기부금공제금액

        Double st_c = ipd_c+med_c+ed_c+dd_c;//특별세액공제금액

        Double sd_ = 0.0;//표준세액공제금액

        if(st_c<130000){
            sd_= 130000.0;
            st_c=0.0;
        } else if (st_c>=130000) {
            sd_=0.0;
        }

        Double rp_d = Double.parseDouble(data.getRd().replaceAll(",",""))*0.15;

        Double dt_a = ca_t-eit_c-st_c-sd_-rp_d;

        if(dt_a<0){
            dt_a=0.0;
        }

        System.out.println("결정세액 ::: "+dt_a);
        System.out.println("퇴직연금세액공제 ::: "+rp_d);

        refundDTO.set결정세액(decFormat.format(dt_a));
        refundDTO.set퇴직연금세액공제(decFormat.format(rp_d));
        refundDTO.set이름(member.getName());

        return refundDTO;

    }
}
