let stompClient = null;
$(function(){
    $("#question").keyup(qKeyupFn);
})
$(document).ready(function(){
    connect();
});
function showMessage(message){
    $("#chat-content").append(message);
    $("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
}
function disconnect(){
//    if(stompClient != null){
//        stompClient.disconnect();
//    }
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
        // @MessageMapping -> 처음연결시
        stompClient.send("/chatbot/",{}, JSON.stringify({'content':'guest'}));
    });
}
function inputTagString(text){
    let now = new Date();
    
}