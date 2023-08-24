package org.project.dev.payment.service;


import lombok.RequiredArgsConstructor;
import org.project.dev.payment.entity.PaymentEntity;
import org.project.dev.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    /*
    TODO
    pgToken = kakao pay에서 발행한 결제 준비 토큰  이걸 가지고 결제 승인으로 보내야됨!
     */
    @Transactional
    public void paymentSuccess(String pgToken) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPgToken(pgToken);
        paymentRepository.save(paymentEntity);



    }
}
