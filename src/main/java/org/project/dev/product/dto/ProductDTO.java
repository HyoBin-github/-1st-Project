package org.project.dev.product.dto;

import lombok.*;
import org.project.dev.member.entity.MemberEntity;
import org.project.dev.product.entity.ProductBrandEntity;
import org.project.dev.product.entity.ProductEntity;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    /*
    Todo
     1. code.aiaru@gmail.com
     2. 상품 관련 DTO 입니다.
     3. x
     4. 1) CreateDate와 UpdateDate는 org.project.dev.utils.BaseEntity 상속을 통해 받았습니다.
        2) 판매글 작성자는 일단 productWriter로 해놨습니다. 추후에 member 쪽과 연동을 하면 될 것 같습니다.
        2023-08-23 : productDisplay -> isProductDisplayed 변경하였습니다.
     */

    private Long id;
    private String productName;
    private String productColor;
    private String productSize;
    private String productDescription;
    private int productHits;
    // 이 부분을 나중에 member 쪽에 연결(?) 하면 될 것 같습니다.
//    private String productWriter;
    private Long productPrice;
//    private String productWriter;
    private Boolean isProductDisplayed;
    private LocalDateTime productCreateTime;
    private LocalDateTime productUpdateTime;

    private ProductBrandEntity productBrandEntity;

    // 송원철 / 장바구니 관련
    private MemberEntity member;


    public static ProductDTO toDTO(ProductEntity productEntity){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setProductName(productEntity.getProductName());
        productDTO.setProductColor(productEntity.getProductColor());
        productDTO.setProductSize(productEntity.getProductSize());
        productDTO.setProductDescription(productEntity.getProductDescription());
        productDTO.setProductHits(productEntity.getProductHits());

//        productDTO.setProductWriter(productEntity.getProductWriter()); // 추후 수정 요망
        productDTO.setProductPrice(productEntity.getProductPrice());
//        productDTO.setProductWriter(productEntity.getProductWriter());
        productDTO.setIsProductDisplayed(productEntity.getIsProductDisplayed());
        productDTO.setProductCreateTime(productEntity.getCreateTime());
        productDTO.setProductUpdateTime(productEntity.getUpdateTime());

        // 송원철 / 상품 등록 시 memberId 가져오기
        if (productEntity.getMember() != null) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setMemberNickName(productDTO.getMember().getMemberNickName());
            productDTO.setMember(memberEntity);
        }

        return productDTO;
    }

}
