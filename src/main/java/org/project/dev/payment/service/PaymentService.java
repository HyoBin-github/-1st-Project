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

    @Transactional
    public void paymentSuccess(String pgToken) {

        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setPgToken(pgToken);

        paymentRepository.save(paymentEntity);

    }
}
