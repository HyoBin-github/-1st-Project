package org.project.dev.admin.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.project.dev.admin.dto.AdminProductDto;
import org.project.dev.utils.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "product_table2")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductEntity extends BaseEntity {

    /*
    Todo
     1. catfather49@gmail.com
     2. admin 페이지 분리 예정이라서 어쩔수 없이 entity 중복되긴하지만 만들었습니다. 분리되면 없어질 예정
     3. 
     4. 
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

    @Column(name = "product_hits")
    private int productHits;

    // 이 부분을 나중에 member 쪽에 연결(?) 하면 될 것 같습니다.
    @Column(name = "product_writer", length = 20, nullable = false)
    private String productWriter;

    @ColumnDefault("true")
    private Boolean productDisplay;

    public static AdminProductEntity toEntity(AdminProductDto adminProductDto) {
        return AdminProductEntity.builder()
                .id(adminProductDto.getId())
                .productColor(adminProductDto.getProductColor())
                .productDescription(adminProductDto.getProductDescription())
                .productDisplay(adminProductDto.getProductDisplay())
                .productHits(adminProductDto.getProductHits())
                .productName(adminProductDto.getProductName())
                .productSize(adminProductDto.getProductSize())
                .productWriter(adminProductDto.getProductWriter())
                .build();
    }
}


