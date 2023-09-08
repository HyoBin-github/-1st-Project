package org.project.dev.review.repository;

import org.project.dev.review.entity.ReviewFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewFileRepository extends JpaRepository<ReviewFileEntity,Long> {
}
