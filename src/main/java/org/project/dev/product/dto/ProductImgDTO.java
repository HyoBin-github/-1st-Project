package org.project.dev.product.dto;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.project.dev.product.entity.ProductImgEntity;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImgDTO {

    /*
   Todo
    1. code.aiaru@gmail.com
    2. 상품 이미지 관련 DTO 입니다.
    3. x
    4. CreateDate와 UpdateDate는 org.project.dev.utils.BaseEntity 상속을 통해 받았습니다.
    */

    private Long id;
    private String productImgOriginalName;
    private String productImgSavedName;
    private String productImgSavedPath;
    private Boolean isProductImgMain;
    private Boolean isProductImgDisplayed;

    private LocalDateTime productImgCreateTime;
    private LocalDateTime productImgUpdateTime;


    public static ProductImgDTO toDTO(ProductImgEntity productImgEntity) {
        ProductImgDTO productImgDTO = new ProductImgDTO();
        productImgDTO.setId(productImgEntity.getId());
        productImgDTO.setProductImgOriginalName(productImgEntity.getProductImgOriginalName());
        productImgDTO.setProductImgSavedName(productImgEntity.getProductImgSavedName());
        productImgDTO.setProductImgSavedPath(productImgEntity.getProductImgSavedPath());
        productImgDTO.setIsProductImgMain(productImgEntity.getIsProductImgMain());
        productImgDTO.setIsProductImgDisplayed(productImgEntity.getIsProductImgDisplayed());
        productImgDTO.setProductImgCreateTime(productImgEntity.getCreateTime());
        productImgDTO.setProductImgUpdateTime(productImgEntity.getUpdateTime());
        return productImgDTO;
    }

}