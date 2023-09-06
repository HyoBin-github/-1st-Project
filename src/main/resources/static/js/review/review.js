$('#ajaxBtn').on('click', ajaxFn);
//$('#produtId').val(),

function ajaxFn(){
    const data= {
        'productId':$('#productId').val(),
        'review':$('#review').val(),
//        'start':$('#star').val(),
    }
     $.ajax({
        type:'POST',
        url:"/review/ajaxWrite",
        data:data,
        success:function(res){
            alert("댓글작성완료");
            console.log(res);
            let reData="<ul id="+ res.id+">";
    		    reData+="<li>"+res.reviewWriter+"</li>";
    		    reData+="<li>"+res.review+"</li>";
                reData+="<li>"+res.createTime+"</li>";
                reData+='<input type="button" value="삭제" onclick="onDelete('+res.id+')">'
                reData+="</ul>";

        $('#tData').append(reData);

    },
    error:()=>{
    	alert("Faill!!");
    }
})
}

function reviewList(){
    const prId = $('#prId').val();
    console.log(prId)
    const data= {
            'productId':$('#productId').val()
            }

    $.ajax({
            url:"/review/reviewList/"+prId,
            data:data,
            type:"get",
            success:function(res){
            var reviewBody = $('#tData'); // tData를 변수에 삽입
            reviewBody.html(''); // tData 초기화
            console.log(res);
            let list ="";
            $.each(res, function(i, content){ // res= return data, i= key, content= value
                list="<ul id="+ content.id+">";
                list+="<li>"+content.reviewWriter+"</li>";
                list+="<li>"+content.review+"</li>";
                list+="<li>"+content.createTime+"</li>";
                list+='<input type="button" value="삭제" onclick="onDelete('+content.id+')">'
                list+="</ul>";


              $('#tData').append(list);
                });
            }


    });

}




function onDelete(id){
    console.log(id)
    console.log("delete")
    var delData= id;
    console.log(delData)
    $.ajax({
        url:"/review/delete/"+id,
        type:'POST',
        data:delData,
        success:function(res){
        if(res==1){
            alert("삭제성공");
        }else{
            alert("삭제실패");
        }
        reviewList();
        },
        error:()=>{
            	alert("Faill!!");
        }

    })

}








