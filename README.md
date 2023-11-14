
# ✈프로젝트 소개
  - 프로젝트 기간 : 2023.08.25 ~ 2023.09.25 <br/>
  - 선정사유 : KREAM이라는 쇼핑몰의 깔끔한 UI와 상품의 직관성이 뚜렷해 참고하였습니다.
  - 시나리오형 챗봇 기능을 구현했습니다.
<br/>

## 1차 팀 구현기능
DB - 개인 <br/>
발표 - 개인 <br/>
ppt 템플릿 - 김＊＊ <br/>
포토샵 - 박＊＊ <br/>
<br/>
회원 카테고리(리스트, 디테일) + 기능(댓글 제외) - ＊＊철  <br/>
상품 카테고리(리스트, 디테일) - 박＊＊  <br/>
게시판 카테고리(리스트, 디테일) + 디자인(댓글) - 김＊＊  <br/>
관리자 카테고리 - 이＊＊ <br/>
댓글 + 챗봇 - 방＊＊


## 🖥 개발 환경
- Language : Java11, Javascript, HTML5, CSS3
- Framework : Spring boot
- IDE : IntelliJ IDEA
- Template Engine : Thymeleaf
- Database : MySQL
- Version Management : Git, Github

## 🔑DB구조
<br/>
- 리뷰(Review) <br/>
&nbsp; 한 회원이 여러개의 댓글을 작성가능하도록 설정하였습니다. <br/>
<details>
  <summary>DB사진</summary>
// DB사진
</details>

## 리뷰 코드
<details>
  <summary>Controller</summary>
  
````  
@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/write")
    public @ResponseBody ReviewDto replyWrite(@ModelAttribute ReviewDto reviewDto,
                                              @AuthenticationPrincipal MyUserDetails myUserDetails){

        ReviewDto replyDto1 = reviewService.reviewWrite(reviewDto, myUserDetails);

        return  replyDto1;
    }

    @GetMapping("/list")
    public @ResponseBody List<ReviewDto> reviewList(@ModelAttribute ReviewDto reviewDto){
        List<ReviewDto> reviewDtos = reviewService.reviewList(reviewDto);
        return reviewDtos;
    }

    @PostMapping("/delete")
    public int reviewDelete(@ModelAttribute ReviewDto reviewDto){
        int rs = reviewService.replyDelete(reviewDto.getId());
        return rs;
    }

    @PostMapping("/up")
    public int reviewUp(@ModelAttribute ReviewDto reviewDto,
                       @AuthenticationPrincipal MyUserDetails myUserDetails){
        int rs = reviewService.reviewUp(reviewDto, myUserDetails);

        return rs;
    }


}

````

</details>

<br/>

<details>
  <summary>Service</summary>

  ````
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ProductRepository productRepository;

    public ReviewDto reviewWrite(ReviewDto replyDto, MyUserDetails myUserDetails) {

        ProductEntity productEntity
                = productRepository.findById(replyDto.getProductId()).orElseThrow(() -> new IllegalArgumentException("X"));

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .review(replyDto.getReview())
                .reviewWriter(myUserDetails.getMemberEntity().getMemberEmail())
                .productEntity(productEntity)
                .build();
        Long reviewId = reviewRepository.save(reviewEntity).getId();

        ReviewEntity reviewEntity1 = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("X"));

        return ReviewDto.builder()
                .id(reviewEntity1.getId())
                .review(reviewEntity1.getReview())
                .reviewWriter(reviewEntity1.getReviewWriter())
                .updateTime(reviewEntity1.getUpdateTime())
                .createTime(reviewEntity1.getCreateTime())
                .productId(reviewEntity1.getProductEntity().getId())
                .build();
    }


    public List<ReviewDto> reviewList(ReviewDto reviewDto) {

        ProductEntity productEntity
                = productRepository.findById(reviewDto.getProductId()).orElseThrow(() -> {
            throw new IllegalArgumentException("X");
        });
        List<ReviewDto> reviewDtos = new ArrayList<>();
        List<ReviewEntity> reviewEntities
                = reviewRepository.findByProductId(productEntity.getId());

        for (ReviewEntity reviewEntity : reviewEntities) {

            reviewDtos.add(ReviewDto.builder()
                    .id(reviewEntity.getId())
                    .review(reviewEntity.getReview())
                    .reviewWriter(reviewEntity.getReviewWriter())
                    .productId(reviewEntity.getProductEntity().getId())
                    .createTime(reviewEntity.getCreateTime())
                    .updateTime(reviewEntity.getUpdateTime())
                    .build());
        }
        return reviewDtos;
    }

    public int replyDelete(Long id) {

        Optional<ReviewEntity> reviewEntity
                = Optional.ofNullable(reviewRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("X");
        }));
        if (reviewEntity.isPresent()) {
            reviewRepository.delete(reviewEntity.get());
            return 1;
        }
        return 0;
    }


    public int reviewUp(ReviewDto reviewDto, MyUserDetails myUserDetails) {

        ProductEntity productEntity
                = productRepository.findById(reviewDto.getProductId()).orElseThrow(()->{
                    throw new IllegalArgumentException("X");
        });
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .id(reviewDto.getId())
                .review(reviewDto.getReview())
                .reviewWriter(myUserDetails.getMemberEntity().getMemberEmail())
                .productEntity(productEntity)
                .build();
        Long saveId = reviewRepository.save(reviewEntity).getId();

        Optional<ReviewEntity> optionalReviewEntity
                = Optional.ofNullable(reviewRepository.findById(saveId).orElseThrow(()->{
                    throw new IllegalArgumentException("X");
        }));
        if (optionalReviewEntity.isPresent()){
            return 1;
        }
        return 0;
    }
}
````
</details>

<br/>

<details>
  <summary>리뷰작성Script</summary>

```javascript
$('#reviewBtn').on('click', reviewFn);

$(document).ready(function(){
    replyList()
});

function reviewFn() {
    const review = $('#review').val()
    const date = {
        'productId': $('#productId').val(),
        'review': $('#review').val()
    }

    if(review!=""){
        $.ajax({
            type: 'POST',
            url: "/review/write",
            data: date,
            success(res) {
                alert("작성완료");
                replyList();
            }
        });
    }else{
        alert("리뷰를 작성해주세요");
    }
    $('#review').val("");
}

function replyList(){
    const productId = $('#proId').val();
    const data = {
        'productId':productId
    }

    $.ajax({
        url:"/review/list",
        data: data,
        type:"get",
        success:function(res){
            var replyBody = $('#reviewCon'); // id가 reviewCon인 곳을 변수지정
            replyBody.html(''); // replyCon 초기화
            let list = "";
            if(res.length<1){
                list="등록된 댓글이 없습니다.";
            }else{
                $(res).each(function(){
                    list = "<ul>";
                    list+="<li class='writer'>"+this.reviewWriter+"</li>";
                    list+="<li class='create'>"+this.createTime+"</li>";
                    list+="<li>";
                    list+="<div id='reCon"+this.id+"' class='reCon'>";
                    list+="<span>"+this.review+"</span>";
                    list+="<div class='Btn'>";
                    list+='<input type="button" value="삭제" onclick="onDelete('+this.id+')">';
                    list+="<input type='button' class='replyUpBtn' value='수정' onclick='showUpDate("+this.id+',"'+this.review+'",'+this.productId+")'>";
                    list+="</div>";
                    list+="</div>";
                    list+="<div id='showUp"+this.id+"' class='show'>";
                    list+="</div>";
                    list+="</li>";
                    list+="</ul>";
                    $('#reviewCon').append(list); // replyCon에 추가
                });
            }
        }
    });
}
````
</details>
<br/>
<details>
  <summary>리뷰 리스트 Script</summary>

  ```
```

</details>



