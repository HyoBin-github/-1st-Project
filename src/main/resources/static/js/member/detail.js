// 파일 선택 시 이미지 미리보기 함수
  document.getElementById('file-input').addEventListener('change', function() {
    var preview = document.getElementById('image-preview');
    var file = this.files[0];
    var reader = new FileReader();

    reader.onload = function(e) {
      // 이전 이미지 삭제
      while (preview.firstChild) {
        preview.removeChild(preview.firstChild);
      }

      var img = new Image();
      img.src = e.target.result;
      img.style.maxWidth = '100%';
      img.style.maxHeight = '100%';
      preview.appendChild(img);

      // 이미지가 선택되면 업로드 버튼 활성화
      document.getElementById('upload-button').disabled = false;
    };

    reader.readAsDataURL(file);
  });
  // 파일이 선택되지 않았을 때 업로드 버튼 비활성화
  function enableUploadButton() {
    var fileInput = document.getElementById('file-input');
    var uploadButton = document.getElementById('upload-button');

    if (fileInput.value) {
      uploadButton.disabled = false;
    } else {
      uploadButton.disabled = true;
    }
  }

 // 생년월일 유효성 검사 함수
 function validateBirth() {
     var birth = document.getElementById('memberBirthSpan').textContent;

     if (!/^[0-9]{8}$/.test(birth)) {
         document.getElementById('birthErrorMessage').textContent = "올바른 형식으로 입력해주세요 (8자리 숫자)";
     } else {
         document.getElementById('birthErrorMessage').textContent = "";
     }
 }
 // 페이지 로드 시 유효성 검사 수행
 window.onload = validateBirth;

// 이미지 삭제 버튼 클릭 시 프로필 이미지 삭제 및 기본 이미지 업로드
function deleteImage() {
  // 프로필 이미지 삭제 요청 보내기
  fetch('/image/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
  })
  .then(response => {
    if (response.ok) {
      // 이미지 삭제 성공 시
      // 이미지 미리보기 영역 초기화
      var preview = document.getElementById('image-preview');
      while (preview.firstChild) {
        preview.removeChild(preview.firstChild);
      }

      // 이미지 업로드 버튼 활성화
      document.getElementById('upload-button').disabled = false;

      // 프로필 이미지가 기본 이미지인 경우
      if (isDefaultImage()) {
        // 프로필 삭제 버튼 비활성화
        document.getElementById('delete-button').disabled = true;
      }
    } else {
      // 이미지 삭제 실패 시 처리
      console.error('이미지 삭제 실패');

      // 이미지 삭제 실패 시에도 기본 이미지를 다시 로드
      loadDefaultImage();
    }
  })
  .catch(error => {
    console.error('이미지 삭제 중 오류 발생:', error);
  });
}

// 현재 이미지가 기본 이미지(default.png)인지 확인하는 함수
function isDefaultImage() {
  var imagePreview = document.getElementById('preview-image');
  return imagePreview && imagePreview.src && imagePreview.src.endsWith('/profileImages/default.png');
}
// 기본 이미지를 다시 로드하는 함수
function loadDefaultImage() {
  var imagePreview = document.getElementById('preview-image');
  imagePreview.src = '/profileImages/default.png';
}


// 기본 이미지 업로드 함수
function uploadDefaultImage() {
  // 기본 이미지 파일 선택
  var defaultImageFile = new File(['default'], 'default.png', { type: 'image/png' });

  // FormData 객체 생성 및 파일 추가
  var formData = new FormData();
  formData.append('file', defaultImageFile);

  // 기본 이미지 업로드 요청 보내기
  fetch('/image/upload', {
    method: 'POST',
    body: formData,
  })
  .then(response => {
    if (response.ok) {
      // 기본 이미지 업로드 성공 시 처리
      console.log('기본 이미지 업로드 성공');
    } else {
      // 기본 이미지 업로드 실패 시 처리
      console.error('기본 이미지 업로드 실패');
    }
  })
  .catch(error => {
    console.error('기본 이미지 업로드 중 오류 발생:', error);
  });
}
