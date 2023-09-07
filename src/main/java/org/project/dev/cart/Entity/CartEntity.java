package org.project.dev.cart.Entity;


import lombok.*;
import org.project.dev.utils.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cart_tb")
public class CartEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "cart_productId")
    private Long productId;

    @Column(name = "member_id")
    private Long memberId;

    // 1이면 결제 된것, 0이면 결제 안된것
    @Column
    private int isPayment;

    // 기본값 1이면 보이고 0이면 안보이게
    @Column
    private int isDisplay;
    
    // 상품 가격
    @Column(name = "cart_productPrice")
    private Integer productPrice;

    // 상품명
    @Column(name = "cart_productName")
    private String productName;




}
