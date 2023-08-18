package org.project.dev.product.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductUtilService {

    /*
    Todo
     1. code.aiaru@gmail.com
     2. CRUD 처리 외의 로직들이 모인 곳입니다.
     3. x
     4. x
     */

    private final ProductRepository productRepository;

    // HITS, DETAIL (SELECT)
    @Transactional
    public void updateHits(Long id) {
        // productRepository의 updateHits 메소드를 호출
        productRepository.updateHits(id);
    }


}
