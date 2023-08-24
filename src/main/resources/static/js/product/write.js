function previewImages(event) {
    const files = event.target.files;
    const previewContainer = document.getElementById('imagePreview');
    previewContainer.innerHTML = "";  // 이전 미리보기 이미지들을 제거합니다.

    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();

        reader.onload = function(e) {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.width = 100;  // 미리보기 이미지의 폭을 조정합니다.
            img.style.marginRight = '10px'; // 이미지 간 간격을 조정합니다.
            previewContainer.appendChild(img);
        }
        reader.readAsDataURL(file);
    }
}
