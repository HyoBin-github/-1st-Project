
# ✈프로젝트 소개
  - 프로젝트 기간 : 2023.08.25 ~ 2023.09.25 <br/>
  - 선정사유 : KREAM이라는 쇼핑몰의 깔끔한 UI와 상품의 직관성이 뚜렷해 참고하였습니다.
  - 시나리오형 챗봇 기능을 구현했습니다.
<br/>
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

<br/>
<br/>

## 🖥 개발 환경
- Language : Java11, Javascript, HTML5, CSS3
- Framework : Spring boot
- IDE : IntelliJ IDEA
- Template Engine : Thymeleaf
- Database : MySQL
- Version Management : Git, Github

<br/>
<br/>

## 🔑DB구조
- 리뷰(Review) <br/>
&nbsp; 한 회원이 여러개의 댓글을 작성가능하도록 설정하였습니다. <br/>

DB구조 사진
<br/>

![DB구조](https://github.com/HyoBin-github/-1st-Project/issues/8#issue-1994988218)


<br/>
<br/>

## 리뷰 코드
<details>
  <summary> 💁‍♂️ Controller</summary>
  
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
  <summary> 🙋‍♂️ Service</summary>

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
  <summary> ✏ 리뷰작성Script</summary>

```javascript
$('#reviewBtn').on('click', reviewFn);

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
  <summary> 📃 리뷰 리스트 Script</summary>

  ```
$(document).ready(function(){
    replyList()
});

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
```

</details>
<br/>
<details>
  <summary> ✂ 리뷰삭제</summary>

````
function onDelete(id){
    console.log(id)
    var dData = {
        'id':id
    }
    $.ajax({
        url:"/review/delete",
        type:'POST',
        data:dData,
        success:function(res){
            if(res==1){
                alert("삭제성공!");
            }else{
                alert("삭제실패!");
            }
            replyList();
        },
        error:()=>{
            alert("Fail!!")
        }
    });
}
````
  
</details>
<br/>

<details>
  <summary> 📝리뷰수정</summary>

````
function showUpDate(id,review,productId){

    console.log(review)
    console.log(id)
    console.log(productId)
    const reId = $('#reCon'+id);
    console.log(reId.hasClass('review_content'));
    if(reId.hasClass('review_content')){
        reId.removeClass('review_content')
    }else{
        reId.addClass('review_content')
         $('#showUp'+id).html(
             "<textarea id='review"+id+"'>"+review+"</textarea>"
            +"<input type='button' class='replyUpBtn' value='완료' onclick='replyUpDate("+id+','+productId+")'>"
         );
    };
}

function replyUpDate(id,productId){
    const data ={
        'review':$('#review'+id).val(),
        'id':id,
        'productId':productId
    };
    console.log(data);
    $.ajax({
        url:"/review/up",
        type:'POST',
        data:data,
        success:function(res){
            if(res==1){
                alert("수정성공")
            }else{
                alert("수정실패")
            }
            replyList();
        }
    });
}
````
  
</details>
<br/><br/>

## 🤖 ChatBot
<details>
  <summary>ChatBot 팝업창</summary>

````
const chatbot = document.querySelector('.chatbot');

chatbot.addEventListener("click",function(){
    let options = "toolbar=no,scrollbars=no,resizable=yes, status=no,menubar=no,width=500,height=600, left=600, top=150";
    window.open("/chatbot/chat",'팝업', options);
});
````
  
</details>
<br/>

<details>
  <summary>ChatBot JavaScript</summary>

````
let stompClient = null;
$(function(){
    $("#question").keyup(qKeyupFn);
    connect();
})
function showMessage(message){
    $("#chat-content").append(message);
    $("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
}
function disconnect(){

    window.close();
}
function connect(){

    let soket = new SockJS('/chatEndpoint');
    stompClient = Stomp.over(soket);
    stompClient.connect({},function (frame){
        console.log('Connected:'+frame);
        stompClient.subscribe('/topic/greetings',function(botMessage){
            showMessage(JSON.parse(botMessage.body).message);
        });
        stompClient.subscribe('/topic/message',function(botMessage){
                    showMessage(JSON.parse(botMessage.body).message);
                });
        // @MessageMapping -> 처음연결시
        stompClient.send("/chat/hello",{}, JSON.stringify({'content':'guest'}));
    });
}
function inputTagString(text){
    let now = new Date();
    let ampm = (now.getHours()>11)?"오후":"오전";
    let time = ampm + now.getHours()%12+":"+now.getMinutes();
    let message = `
    <div class="msg_user_flex_end">
    <div class="message">
    <div class="part">
    <p>${text}</p>
    </div>
    <div class="time">${time}</div>
    </div>
    </div>`;
    return message;
}
function menuclickFn(event){
    let text = event.target.innerText.trim();
    let message = inputTagString(text);
    showMessage(message);
    stompClient.send("/chat/message", {}, JSON.stringify({'content':text}));
}
function qKeyupFn(event){
    if(event.keyCode!=13) return;
    msgSendClickFn()
}
function msgSendClickFn(){
    let question = $('#question').val().trim();
    if(question==""||question.length<2) return;
    let message = inputTagString(question);
    showMessage(message);
    $('#question').val("");
    stompClient.send("/chat/message",{},JSON.stringify({'content':question}));
}
````
  
</details>

<br/>
<details>
  <summary>ChatBot Controller</summary>

````
@Controller
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatBotController {

    @GetMapping("/chat")
    public String chatbot(){
        return "chatbot/chat";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public BotMessage chatSt() throws Exception{
        Thread.sleep(50);
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        String formattedDay = today.format(formatter);
        String formattedTime = today.format(DateTimeFormatter.ofPattern("a H:mm"));

        // 처음 실행되는 -> 답장문
        return new BotMessage("<div class='flex_center_date' >"+formattedDay+"</div>"+
                "<div class='msg_bot_flex'>"+
                "<div class='icon'>"+
                "<img src='/images/chatbot2.png'  th:alt=\"#{chat}\" />" +
                "</div>"+
                "<div class='message'>"+
                "<div class='part'>"+
                "<p style='text-align:center'>안녕하세요, 챗봇입니다. <br> 궁금한 점은 저에게 물어보세요!</p>"+
                "</div>" +
                "<div class='part2'>"+
                "<p>아래는 자주하는 질문 내용을 클릭이나 입력해 주세요.</p>"+
                "<div class='flex_center_menu'>"+
                "<div class='menu-item'><span onclick='menuclickFn(event)'>상품문의</span></div>"+
                "<div class='menu-item'><span onclick='menuclickFn(event)'>결제문의</span></div>"+
                "<div class='menu-item'><span onclick='menuclickFn(event)'>배송문의</span></div>"+
                "</div>"+
                "</div>"+
                "<div class='time'>"+
                formattedTime+
                "</div>"+
                "</div>"+
                "</div>");
    }
    @MessageMapping("/message")
    @SendTo("/topic/message")
    public BotMessage message(ClientMessage message) throws Exception{
        Thread.sleep(100);
        LocalDateTime today = LocalDateTime.now();
        String formattedTime = today.format(DateTimeFormatter.ofPattern("a H:mm"));
        String responseText = message.getContent()+"에 대한 안내입니다.";

        return new BotMessage("<div class='msg_bot_flex'>"+
                "<div class='icon'>"+
                "<img src='/images/chatbot2.png'  th:alt=\"#{chat}\" />" +
                "</div>"+
                "<div class='message'>"+
                "<div class='part'>"+
                "<p>"+responseText+"</p>"+
                "</div>"+
                "<div class='time'>"+
                formattedTime+
                "</div>"+
                "</div>"+
                "</div>");
    }

}
````
  
</details>
<br/>
<details>
  <summary>ChatBot WebSocketConfig</summary>

````
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/chat");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chatEndpoint").withSockJS();
    }
}
````
</details>
