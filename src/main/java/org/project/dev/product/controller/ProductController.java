package org.project.dev.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.dev.cartNew.entity.CartEntity;
import org.project.dev.cartNew.entity.CartItemEntity;
import org.project.dev.cartNew.service.CartService;
import org.project.dev.cartNew.service.SemiCartService;
import org.project.dev.config.member.MyUserDetails;
import org.project.dev.config.semiMember.SemiMyUserDetails;
import org.project.dev.member.entity.MemberEntity;
import org.project.dev.member.entity.SemiMemberEntity;
import org.project.dev.member.service.MemberService;
import org.project.dev.member.service.SemiMemberService;
import org.project.dev.product.dto.ProductBrandDTO;
import org.project.dev.product.dto.ProductImgDTO;
import org.project.dev.product.entity.ProductBrandEntity;
import org.project.dev.product.entity.ProductEntity;
import org.project.dev.review.dto.ReviewDto;
import org.project.dev.review.entity.ReviewEntity;
import org.project.dev.review.repository.ReviewRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.project.dev.product.dto.ProductDTO;
import org.project.dev.product.service.ProductService;
import org.project.dev.product.service.ProductUtilService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Slf4j // 송원철, log
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
    private final ReviewRepository reviewRepository;
    private final MemberService memberService; // 송원철 / 장바구니 관련
    private final CartService cartService; // 송원철 / 장바구니 관련
    private final SemiMemberService semiMemberService; // 송원철 / 장바구니 관련
    private final SemiCartService semiCartService; // 송원철 / 장바구니 관련
    

    // WRITE (INSERT)
    // 게시물 작성 페이지
//    @GetMapping("/write")
//    public String getProductWrite() {
//        return "/product/write";
//    }
//
//
//    // WRITE PROCESS (INSERT)
//    // 게시물 작성 처리 시
//    @PostMapping("/write")
//    public ResponseEntity<Map<String,Object>> postProductWrite(@ModelAttribute ProductDTO productDTO,
//                                                  @RequestParam(name = "productImages", required = false) List<MultipartFile> productImages) throws IOException {
//        ProductEntity productEntityWritePro = productService.productWriteDetail(productDTO); // 상품글 작성
//        productUtilService.saveProductImages(productEntityWritePro, productImages); // 이미지 저장
//        System.out.println("productImages: " + productImages);
//        Map<String,Object> response = new HashMap<>();
//        response.put("status","success");
//        response.put("redirectUrl","/product/index");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    // 송원철 / write에 로그인한 memberId 담기
    @GetMapping("/write")
    public String getProductWrite(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {

        MemberEntity member=myUserDetails.getMemberEntity();

        if (member != null) {
            model.addAttribute("member", member);
            log.info("MemberId: {}", member.getMemberId());
        }else {
            log.info("member is null");
        }
        return "/product/write";
    }

    // 송원철 / write 시 로그인한 memberId, memberNickName 저장
    @PostMapping("/write")
    public ResponseEntity<Map<String,Object>> postProductWrite(@ModelAttribute ProductDTO productDTO,
                                                               @ModelAttribute ProductBrandDTO productBrandDTO,
                                                               @RequestParam(name = "productImages", required = false) List<MultipartFile> productImages,
                                                               @AuthenticationPrincipal MyUserDetails myUserDetails) throws IOException {
        MemberEntity member = myUserDetails.getMemberEntity(); // 현재 로그인한 사용자의 MemberEntity 가져오기

        if (member == null) {
            // 사용자 정보가 없는 경우 로그를 남깁니다.
            log.info("사용자 정보가 없습니다.");
        }

        // 브랜드 정보 작성 또는 가져오기
        ProductBrandEntity productBrandEntity = productService.productBrandWriteDetail(productBrandDTO);
        // 상품글 작성
        ProductEntity productEntityWritePro = productService.productWriteDetail(productDTO, productBrandEntity, member);
        // 이미지 저장
        productUtilService.saveProductImages(productEntityWritePro, productImages);

        Long productId = productEntityWritePro.getId(); // 작성한 글의 productId를 가져옴.

        Map<String,Object> response = new HashMap<>();
        response.put("status","success");
        response.put("redirectUrl","/product/"+productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
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

    // Cursor-Based List
    @GetMapping("/cursorBasedList")
    public String cursorBasedList(@RequestParam(required = false) Long lastId, Model model) {
        int limit = 10; // 페이지당 표시할 개수
        List<ProductDTO> productDTOS = productService.productCursorBasedList(lastId, limit);
        model.addAttribute("products", productDTOS);

        if (!productDTOS.isEmpty()) {
            model.addAttribute("nextCursor", productDTOS.get(productDTOS.size() - 1).getId());
        }

        return "/product/cursorBasedList";
    }


    // DETAIL (SELECT)
//    @GetMapping("/{id}")
//    public String getProductDetail(@PathVariable Long id, Model model) {
//        // updateHits 메소드를 호출, 해당 게시글의 조회수를 하나 올린다.
//        productUtilService.updateHits(id);
//        ProductDTO productDTOViewDetail = productService.productViewDetail(id);
//        List<ProductImgDTO> productImgDTOS = productUtilService.getProductImagesByProductId(id);
//
//        // dto 해야댐
//        List<ReviewDto> reviewDtos = reviewService.reviewList(productDTOViewDetail.getId());
//
//        model.addAttribute("product", productDTOViewDetail);
//        model.addAttribute("productImages", productImgDTOS);
//        model.addAttribute("reviews", reviewDtos);
//        return "/product/detail";
//    }

    // 송원철 - member, semiMember 추가
    // 로그인 안한 사람, member, semiMember 모두 접근 가능
    @GetMapping("/{id}")
    public String getProductDetail(@PathVariable Long id, Model model,
                                   @AuthenticationPrincipal MyUserDetails myUserDetails,
                                   @AuthenticationPrincipal SemiMyUserDetails semiMyUserDetails) {
        // updateHits 메소드를 호출, 해당 게시글의 조회수를 하나 올린다.
        productUtilService.updateHits(id);
        ProductDTO productDTOViewDetail = productService.productViewDetail(id);
        List<ProductImgDTO> productImgDTOS = productUtilService.getProductImagesByProductId(id);


        MemberEntity member = myUserDetails != null ? myUserDetails.getMemberEntity() : null;
        SemiMemberEntity semiMember = semiMyUserDetails != null ? semiMyUserDetails.getSemiMemberEntity() : null;

        model.addAttribute("product", productDTOViewDetail);
        model.addAttribute("productImages", productImgDTOS);

        // 추가: member가 null이 아닌 경우에만 memberId를 model에 추가
        if (member != null) {
            MemberEntity loginMember = memberService.findMember(member.getMemberId());

            int cartCount = 0;
            CartEntity memberCart = cartService.findMemberCart(loginMember.getMemberId());
            List<CartItemEntity> cartItems = cartService.allMemberCartView(memberCart);

            for(CartItemEntity cartItem : cartItems) {
                cartCount += cartItem.getCartItemCount();
            }
            model.addAttribute("cartCount", cartCount);
            model.addAttribute("member", member);
            log.info("Member: {}", member.getMemberId());
        }else {
            log.info("Member is null");
        }

        if (semiMember != null) {
            SemiMemberEntity loginSemiMember = semiMemberService.findSemiMember(semiMember.getSemiMemberId());

            int cartCount = 0;
            CartEntity semiMemberCart = semiCartService.findSemiMemberCart(loginSemiMember.getSemiMemberId());
            List<CartItemEntity> cartItems = semiCartService.allSemiMemberCartView(semiMemberCart);

            for(CartItemEntity cartItem : cartItems) {
                cartCount += cartItem.getCartItemCount();
            }
            model.addAttribute("cartCount", cartCount);
            model.addAttribute("semiMember", semiMember);
            log.info("SemiMember: {}", semiMember.getSemiMemberId());
        }else {
            log.info("SemiMember is null");
        }
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

    // 송원철 - 상품관리 페이지
    @GetMapping("/manage")
    public String getProductManage(){
        return "/product/manage";
    }

}




