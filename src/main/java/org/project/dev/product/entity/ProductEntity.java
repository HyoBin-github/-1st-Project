package org.project.dev.product.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.project.dev.category.CategoryEntity;
import org.project.dev.product.dto.ProductDTO;
import org.project.dev.utils.BaseEntity;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "product_table")
public class ProductEntity extends BaseEntity {

    /*
    Todo
     1. code.aiaru@gmail.com
     2. 상품 관련 Entity 입니다.
     3. x
     4. 1) CreateDate와 UpdateDate는 org.project.dev.utils.BaseEntity 상속을 통해 받았습니다.
        2) 판매글 작성자는 일단 productWriter로 해놨습니다. 추후에 member 쪽과 연동을 하면 될 것 같습니다.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_color", nullable = false)
    private String productColor;

    @Column(name = "product_size", nullable = false)
    private String productSize;

    @Column(name = "product_description", length = 500, nullable = false)
    private String productDescription;

    @Column
    private int productHits;

    // 이 부분을 나중에 member 쪽에 연결(?) 하면 될 것 같습니다.
    @Column(name = "product_writer", length = 20, nullable = false)
    private String productWriter;

    @ColumnDefault("true")
    private Boolean productDisplay;


    public static ProductEntity toEntity(ProductDTO productDTO){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productDTO.getId());
        productEntity.setProductName(productDTO.getProductName());
        productEntity.setProductColor(productDTO.getProductColor());
        productEntity.setProductSize(productDTO.getProductSize());
        productEntity.setProductDescription(productDTO.getProductDescription());
        productEntity.setProductHits(productDTO.getProductHits());
        productEntity.setProductWriter(productDTO.getProductWriter()); // 추후 수정 요망
        productEntity.setProductDisplay(productDTO.getProductDisplay());
        return productEntity;
    }


}
