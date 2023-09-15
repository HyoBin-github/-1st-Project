package org.project.dev.admin.repository;

import org.project.dev.admin.entity.AdminProductEntity;
import org.project.dev.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/*


 */
@Repository
public interface AdminProductRepository extends JpaRepository<ProductEntity,Long>  {

    @Query(value = "select * from product_table1 where member_id = :memberId", nativeQuery = true)
    List<ProductEntity> getMemberIdProductList(int memberId);
}
