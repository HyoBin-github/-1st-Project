package org.project.dev.payment.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.payment.dto.PaymentDto;
import org.project.dev.payment.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class paymentController {

    private final PaymentService paymentService;
    @PostMapping("/request")
    public Map<String,Object> request(
            @RequestBody PaymentDto paymentDto,
            @RequestParam("memberid") String memberId){

        return null;
    }

    @GetMapping("/success")
    public Map<String,Object> success(
            @RequestParam("pg_token") String pgToken){

        paymentService.paymentPrepare(pgToken);

        return null;
    }

    @PostMapping("/{pg}/pg")
    public Map<String,Object> pgRequest(
            @PathVariable("pg") String pg){

        paymentService.pgRequest(pg);

        return null;
    }

    @PostMapping("/fail")
    public Map<String,Object> fail(
            @RequestBody PaymentDto paymentDto,
            @RequestParam("memberid") String memberId){

        return null;
    }
}
