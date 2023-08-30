$('#ajaxBtn').on('click', ajaxFn);
//$('#produtId').val(),

function ajaxFn(){
    const data= {
        'productId':$('#productId').val(),
        'review':$('#review').val(),
//        'start':$('#star').val(),

    }



//    var form = new FormData();
//    form.append("newFile", $("#upload_File")[0].files[0]);
//    const formData = new FormData();
//    formData.append("file",file)
//        formData.append("productId",$('#productId').val())
//           formData.append("review",$('#review').val())
//         formData.append("star",$('#star').val())
//    console.log(data);

     $.ajax({
        type:'POST',
        url:"/review/ajaxWrite",
        data:data,
        success:function(res){
            alert("댓글작성완료");
            console.log(res);
            let reData="<ul id="res.id">";
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


function onDelete(id){
    console.log(id)
    console.log("delete")

      $.ajax({
            type:'POST',
            url:"/review/delete/"+id,
            data:data, //body
            success:function(res){
                alert("삭제완료 ");
                console.log(res);

                let reData="<ul id="res.id">";
                    		    reData+="<li>"+res.reviewWriter+"</li>";
                    		    reData+="<li>"+res.review+"</li>";
                                reData+="<li>"+res.createTime+"</li>";
                                reData+='<input type="button" value="삭제" onclick="onDelete('+res.id+')">'
                                reData+="</ul>";

                        $('#tData').append(reData);

        },
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

