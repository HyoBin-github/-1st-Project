//package org.project.dev.cart.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.project.dev.cart.dto.CartDto;
//import org.project.dev.cart.service.CartService;
//import org.springframework.security.core.parameters.P;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/cart")
//@RequiredArgsConstructor
//public class CartController {
//
//    private final CartService cartService;
//
//    @PostMapping("/{memberId}/{productId}")
//    public String postCartInsert(@PathVariable("memberId") Long memberId,
//                                 @PathVariable("productId") Long productId,
//                                 @RequestParam("productName") String productName,
//                                 @RequestParam("productPrice") Integer productPrice){
//
//        cartService.cartInsert(memberId, productId, productName, productPrice);
//
//        return "redirect:/product/"+productId; // controller로 바로 쏴주기
//        //리다이렉션  상품 디테일로 이동
//    }
//
//    @GetMapping("/{memberId}")
//    public String getCart(@PathVariable("memberId") Long memberId, Model model){
//
//       List<CartDto> cartDtoList = cartService.getMyCartList(memberId);
//
//       model.addAttribute("cartDtoList", cartDtoList);
//
//       return "cart/mycart";
//    }
//
//
//
//}
