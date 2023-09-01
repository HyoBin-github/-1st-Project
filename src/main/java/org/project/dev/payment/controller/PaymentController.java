package org.project.dev.payment.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.payment.dto.PaymentDto;
import org.project.dev.payment.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
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

    @GetMapping("/paymentTestPg")
    public String testPg() {

        return "payment/paymentIndex";

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
    2.productId, total price
    결제 버튼 클릭시  제일 먼저 시작
     */
    @GetMapping("/{pg}/pg")
    @ResponseBody
    public Map<String, Object> pgRequest(
            @PathVariable("pg") String pg,
            @RequestParam("productId") Long productId,
            @RequestParam("memberId") Long memberId,
            @RequestParam("productPrice") Long productPrice,
            @RequestParam("productName") String productName
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        String approvalUrl = paymentService.pgRequest(pg, productId, memberId, productPrice, productName);
        map.put("approvalUrl", approvalUrl);
        return map;
    }

    @PostMapping("/fail")
    public Map<String, Object> fail(
            @RequestBody PaymentDto paymentDto,
            @RequestParam("memberid") String memberId) {

        return null;
    }
}
