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

    private String pgToken;
}
