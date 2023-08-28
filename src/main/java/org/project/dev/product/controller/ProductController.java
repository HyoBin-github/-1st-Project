package org.project.dev.product.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.product.dto.ProductImgDTO;
import org.project.dev.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.project.dev.product.dto.ProductDTO;
import org.project.dev.product.service.ProductService;
import org.project.dev.product.service.ProductUtilService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    /*
    Todo
     1. code.aiaru@gmail.com
     2. 상품 관련 CRUD 입니다.
        각 html(detail, list, update, write) 기본 로직들은 만들어놨으니 확인 부탁드려요.
        list.html는 페이징 처리 때문에 list.css를 만들어 뒀습니다. 필수 로직이 있으니 작업 전에 물어봐주세요.
     3. x
     4. 만약 저번에 얘기가 나왔던 것처럼 판매자 접속 URL(판매자 전용 페이지)을 따로 만든다면,
        1) Write, Update 쪽은 판매자 접속 URL 쪽으로 넘기고,
        2) Detail, List는 각 2개 씩 만들어서 하나는 판매자 전용, 하나는 회원/비회원 상품 조회용으로 만들어도 될 것 같습니다.
        이 부분은 같이 상의를...
     */


    private final ProductService productService;
    private final ProductUtilService productUtilService;


    // WRITE (INSERT)
    // 게시물 작성 페이지
    @GetMapping("/write")
    public String getProductWrite() {
        return "/product/write";
    }


    // WRITE PROCESS (INSERT)
    // 게시물 작성 처리 시
    @PostMapping("/write")
    public String postProductWrite(@ModelAttribute ProductDTO productDTO,
                                   @RequestParam String imageOrders,
                                   @RequestParam(name = "files", required = false) List<MultipartFile> files) throws IOException {
        ProductEntity productEntityWritePro = productService.productWriteDetail(productDTO); // 상품글 작성
        productUtilService.saveProductImages(productEntityWritePro, files, imageOrders); // 이미지 저장
        System.out.println("Image Orders: " + imageOrders);
        return "index";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(name = "searchType", required = false) String searchType,
                       @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {

        ProductService.ProductListResponse response = productService.getProductList(page, pageable, searchType, searchKeyword);
        model.addAttribute("productList", response.getProductList());
        model.addAttribute("nowPage", response.getNowPage());
        model.addAttribute("startPage", response.getStartPage());
        model.addAttribute("endPage", response.getEndPage());
        model.addAttribute("totalPage", response.getTotalPage());
        model.addAttribute("searchType", response.getSearchType());
        model.addAttribute("searchKeyword", response.getSearchKeyword());

        List<ProductImgDTO> productImgDTOS = productUtilService.getMainProductImage(response.getProductList().getContent());
        model.addAttribute("productImages", productImgDTOS);

        return "/product/list";
    }

    // DETAIL (SELECT)
    @GetMapping("/{id}")
    public String getProductDetail(@PathVariable Long id, Model model) {
        // updateHits 메소드를 호출, 해당 게시글의 조회수를 하나 올린다.
        productUtilService.updateHits(id);
        ProductDTO productDTOViewDetail = productService.productViewDetail(id);
        List<ProductImgDTO> productImgDTOS = productUtilService.getProductImagesByProductId(id);

        model.addAttribute("product", productDTOViewDetail);
        model.addAttribute("productImages", productImgDTOS);

        return "/product/detail";
    }

    // UPDATE (UPDATE)
    @GetMapping("/update/{id}")
    public String getProductUpdate(@PathVariable Long id, Model model) {
        ProductDTO productDTOViewDetail = productService.productViewDetail(id);
        List<ProductImgDTO> productImgDTOS = productUtilService.getProductImagesByProductId(id);
        model.addAttribute("productUpdate", productDTOViewDetail);
        model.addAttribute("productImages", productImgDTOS);
        return "/product/update";
    }

    // UPDATE PROCESS (UPDATE)
    @PostMapping("/update")
    public String postProductUpdate(@ModelAttribute ProductDTO productDTO, Model model) {
        ProductDTO productDTOUpdatePro = productService.productUpdateDetail(productDTO);
        model.addAttribute("product", productDTOUpdatePro);
        return "/product/detail";
    }

    // DELETE (DELETE)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/product/list";
    }


}




