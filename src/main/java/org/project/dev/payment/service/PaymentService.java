package org.project.dev.payment.service;


import lombok.RequiredArgsConstructor;
import org.project.dev.payment.entity.KakaoPayPrepareEntity;
import org.project.dev.payment.dto.KakaoPayPrepareDto;
import org.project.dev.payment.entity.PaymentEntity;
import org.project.dev.payment.repository.PaymentRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    /*
    TODO
    pgToken = kakao pay에서 발행한 결제 준비 토큰  이걸 가지고 결제 승인으로 보내야됨!
    구매자가 상품을 장바구니에 담고! 결제를 누르면 kakao payd에서 발행한 url로 페이지 이동이 되고!
    모바일로 qr코드를 찍고 결제 완료를 누르고! 발행된 pgtoken으로 다시 kakao pay로 결제 요청!을 pg 토큰과 tid를 가지고 전송!
    하면 최종적으로 결제가 완료가 됨!
     */

    /*
    TODO
    catfather49@gmail.com 
    결제 "준비" 승인 아님 카카오에서 요청을 날리고 redirect를 받아서 tic pg_token db에 저장
     */
    @Transactional
    public void paymentPrepare(String pgToken) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPgToken(pgToken);
        paymentRepository.save(paymentEntity);


    }

    /*
    토스 카카오 네이버 기타등등 pg로 들어오는 거에 따라서 해당 api에 결제 요청을 보냄

     */
    @Transactional
    public KakaoPayPrepareDto pgRequest(String pg) {
        RestTemplate restTemplate = new RestTemplate();

        if (pg.equals("kakao")) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization","KakaoAK 6bf92b429a38f0eabe31b6d0642a9a24");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            URI uri = UriComponentsBuilder
                    //kakao api 주소
                    .fromUriString("https://kapi.kakao.com")
                    .path("/v1/payment/ready")
                    .queryParam("cid", "TC0ONETIME")
                    .queryParam("partner_order_id", "1001")
                    .queryParam("partner_user_id", "test")
                    .queryParam("item_name", "test_item")
                    .queryParam("quantity", "1")
                    .queryParam("total_amount", "1000")
                    .queryParam("tax_free_amount", "100")
                    .queryParam("approval_url", "http://localhost:8111/payment/success")
                    .queryParam("cancel_url", "http://localhost:8111/payment/cancel")
                    .queryParam("fail_url", "http://localhost:8111/payment/fail")
                    .encode()
                    .build()
                    .toUri();

            ResponseEntity<KakaoPayPrepareDto> result = restTemplate.exchange(uri,HttpMethod.POST,entity, KakaoPayPrepareDto.class);



            result.getBody();

            return null;
        } else {
            throw new RuntimeException("제휴되지 않은 결제 업체 입니다.!!!!");
        }
    }
}
