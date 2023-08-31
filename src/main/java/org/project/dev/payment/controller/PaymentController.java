package org.project.dev.payment.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.payment.dto.PaymentDto;
import org.project.dev.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/request")
    public Map<String, Object> request(
            @RequestBody PaymentDto paymentDto,
            @RequestParam("memberid") String memberId) {

        return null;
    }

    /*
    TODO

    paymentId 어떻게??
     */
    @GetMapping("approval/{paymentId}")
    public Map<String, Object> success(
            @PathVariable(name = "paymentId") Long paymentId,
            @RequestParam("pg_token") String pgToken) {
        Map<String, Object> paymentMap = new HashMap<String, Object>();
        paymentService.paymentPrepare(pgToken, paymentId);

        return null;
    }


    /*
    TODO
    1.catfather49@gmail.com
    2.productId, cartId, totalPrice, itemPrice, itemName,
    3. return 으로 result pc 앱 결제 url 만 설정
     */
    @GetMapping("/{pg}/pg")
    public Map<String, Object> pgRequest(
            @PathVariable("pg") String pg,
            @RequestParam("memberId") Long memberId,
            @RequestParam("productId") Long productId,
            @RequestParam("cartId") Long cartId,
            @RequestParam("totalPrice") Long totalPrice
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        String paymentURL = paymentService.pgRequest(pg,memberId,productId,cartId,totalPrice);
        result.put("url", paymentURL);
        return result;
    }

    @PostMapping("/fail")
    public Map<String, Object> fail(
            @RequestBody PaymentDto paymentDto,
            @RequestParam("memberid") String memberId) {

        return null;
    }
}
