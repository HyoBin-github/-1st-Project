package org.project.dev.cart.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.cart.Entity.CartEntity;
import org.project.dev.cart.Repository.CartRepository;
import org.project.dev.cart.dto.CartDto;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public void cartInsert(Long memberId, Long productId, String productName, Integer productPrice) {

        CartEntity cartEntity = new CartEntity();
        cartEntity.setMemberId(memberId);
        cartEntity.setProductId(productId);
        cartEntity.setProductName(productName);
        cartEntity.setProductPrice(productPrice);

       Long cartId = cartRepository.save(cartEntity).getCartId();

       //cart id 안가져 왔을때 예외 처리 해야 함
        if(cartId==null){
            throw new IllegalArgumentException("cartId가 생성되지않았습니다");
        }
    }

    public List<CartDto> getMyCartList(Long memberId) {

        List<CartDto> cartDtoList=new ArrayList<CartDto>();
        List<CartEntity> cartEntityList=cartRepository.findAllByMemberId(memberId);

        for (CartEntity cartEntity: cartEntityList){
            cartDtoList.add(CartDto.toDto(cartEntity));
        }

        return cartDtoList;
    }


}
