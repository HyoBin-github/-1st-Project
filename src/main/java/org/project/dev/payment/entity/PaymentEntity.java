package org.project.dev.payment.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "payment_tb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private String pgToken; //kakao pay 에서 발행한 토큰 이게 있어야 결제 승인을 날림!

    private String tid; // kakao pay 에서 결제 번호! 이게 있어야 결제 승인을 날림!

    private int isSucced; // 최종적으로 redirect가 와서 성공 했는지?? = 사용자에게 카톡으로 결제 완료가 왔는지?
}
