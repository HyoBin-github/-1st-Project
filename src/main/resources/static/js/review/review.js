$('#ajaxBtn').on('click', ajaxFn);

function ajaxFn(){
    const data={
        'productId':$('#productId').val(),
        'review':$('#review').val(),
    }
//    var form = new FormData();
//    form.append("newFile", $("#upload_File")[0].files[0]);

    console.log(data);

     $.ajax({
        type:'POST',
        url:"/review/ajaxWrite",
        data:data,
        success:function(res){
            alert("댓글작성완료");
            console.log(res);

            let reData="<ul>";
    		    reData+="<li>"+res.reviewWriter+"</li>";
    		    reData+="<li>"+res.review+"</li>";
                reData+="<li>"+res.createDate+"</li>";
                reData+="</ul>";

        $('#tData').append(reData);
    },
    error:()=>{
    	alert("Faill!!");
    }
})
}
//$('#deleteBtn').on('click', deleteFn);
//
//function deleteFn(){
//var id = {
//'reviewId':$('#reviewId').val()
//
//}
//console.log(id);
//
//$.ajax{
//type:'POST',
//url:'/review/delete/'+ id,
//success:function(result){
//console.log('result:'+ result);
//if(result === 'success'){
//alert("댓글이 삭제되었습니다.");
//modal.modal('hide');
//loadJSONData();
//}
//}
//}
//}
//
//
//}

