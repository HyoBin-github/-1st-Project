package org.project.dev.cartNew.dto;

import lombok.*;
import org.project.dev.cartNew.entity.CartEntity;
import org.project.dev.product.entity.ProductEntity;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private Long cartItemId;

    private CartEntity cart;

    // 상품
    private ProductEntity product;

    // 상품 갯수
    private int cartItemCount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
