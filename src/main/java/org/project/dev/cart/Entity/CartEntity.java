package org.project.dev.cart.Entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cart_tb")
public class CartEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "cart_productIdList")
    private String cartProductIdList;

    @Column
    private int isPayment;

    @Column(name = "cart_totalPrice")
    private Integer cartTotalPrice;





}
