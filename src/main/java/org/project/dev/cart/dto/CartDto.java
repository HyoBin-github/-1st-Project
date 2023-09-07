//package org.project.dev.cart.dto;
//
//import lombok.*;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.project.dev.cart.Entity.CartEntity;
//
//import javax.persistence.Column;
//import java.time.LocalDateTime;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Setter
//@Getter
//public class CartDto {
//
//    private Long cartId;
//
//    private Long productId;
//
//    private Long memberId;
//
//    // 1이면 결제 된것, 0이면 결제 안된것
//    private int isPayment;
//
//    // 기본값 1이면 보이고 0이면 안보이게
//    private int isDisplay;
//
//    // 상품 가격
//    private Integer productPrice;
//
//    // 상품명
//    private String productName;
//
//    private LocalDateTime CreateTime;
//
//    private LocalDateTime UpdateTime;
//
//    // entity → dto
//    public static CartDto toDto (CartEntity cartEntity){
//        return CartDto.builder()
//                .cartId(cartEntity.getCartId())
//                .productId(cartEntity.getProductId())
//                .memberId(cartEntity.getMemberId())
//                .isPayment(cartEntity.getIsPayment())
//                .isDisplay(cartEntity.getIsDisplay())
//                .productPrice(cartEntity.getProductPrice())
//                .productName(cartEntity.getProductName())
//                .CreateTime(cartEntity.getCreateTime())
//                .UpdateTime(cartEntity.getUpdateTime())
//                .build();
//
//    }
//
//}
