package org.project.dev.admin.repository;

import org.project.dev.admin.entity.AdminProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
    TODO
    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    ProductEntity mapping 관계때문에 일단 가져온것
    나중에 분리할때 entity 만들어야함

 */
@Repository
public interface AdminProductRepository extends JpaRepository<AdminProductEntity,Long>  {
    List<AdminProductEntity> findByMemberId(Long memberId);
}
