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



}