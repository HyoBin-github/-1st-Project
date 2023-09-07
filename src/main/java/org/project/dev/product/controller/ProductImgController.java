package org.project.dev.product.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.product.service.ProductUtilService;
import org.project.dev.utils.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProductImgController {

    private final ProductUtilService productUtilService;
    private final FileStorageService fileStorageService;

    // AJAX 이미지 저장
    @PostMapping("/product/uploadImage")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        Map<String, Object> result = new HashMap<>();
        try {
            // 이미지 저장 로직
            String savedPath = fileStorageService.storeFile("product", file);
            result.put("status", "success");
            result.put("savedPath", savedPath);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
