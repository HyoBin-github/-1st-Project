
const chatbot = document.querySelector('.chatbot');

chatbot.addEventListener("click",function(){
console.log(1)
    window.open("/chatbot/chat",'팝업','width=400,height=500, left=600, top=150');
})