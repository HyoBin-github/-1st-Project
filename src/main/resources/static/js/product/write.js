// 문서의 모든 컨텐츠가 로드되면 함수를 실행합니다.
document.addEventListener('DOMContentLoaded', function() {
    // HTML 문서에서 필요한 요소를 선택합니다.
    let dragDropArea = document.getElementById('dragDropArea');
    let fileInput = document.getElementById('fileInput');
    let collectedFiles = []; // 선택된 파일을 저장할 배열을 초기화합니다.

    // dragDropArea 요소 위에서 드래그 이벤트가 일어나면 실행되는 이벤트 리스너를 추가합니다.
    dragDropArea.addEventListener('dragover', function(e) {
        e.preventDefault(); // 기본 동작을 막습니다.
        e.stopPropagation(); // 이벤트 전파를 막습니다.
    });

    // 파일이 dragDropArea에 드롭되면 실행되는 이벤트 리스너를 추가합니다.
    dragDropArea.addEventListener('drop', function(e) {
        e.preventDefault(); // 기본 동작을 막습니다.
        e.stopPropagation(); // 이벤트 전파를 막습니다.
        let files = e.dataTransfer.files; // 드롭된 파일을 가져옵니다.
        handleFiles(files); // 파일을 처리하는 함수를 호출합니다.
    });

    // fileInput에서 파일이 선택되면 실행되는 이벤트 리스너를 추가합니다.
    fileInput.addEventListener('change', function(e) {
        let files = e.target.files; // 선택된 파일을 가져옵니다.
        handleFiles(files); // 파일을 처리하는 함수를 호출합니다.
    });

    // 파일을 처리하는 함수입니다.
    function handleFiles(files) {
        let newFileOrders = []; // 새로운 파일 순서를 저장할 배열을 초기화합니다.
        let promises = []; // 비동기 작업을 저장할 Promise 배열을 초기화합니다.

        // 로딩 창을 표시합니다.
        document.getElementById('loading').style.display = 'block';

        // 각 파일에 대해서 반복합니다.
        for (let i = 0; i < files.length; i++) {
            let file = files[i]; // 현재 파일을 가져옵니다.
            collectedFiles.push(file); // 파일을 collectedFiles 배열에 추가합니다.
            let reader = new FileReader(); // FileReader 객체를 생성합니다.

            // FileReader 작업을 Promise로 감쌉니다.
            let promise = new Promise((resolve) => {
                // 파일 읽기가 완료되면 실행될 콜백 함수를 정의합니다.
                reader.onload = function(e) {
                    // 미리보기 이미지를 생성합니다.
                    let imgPreview = document.createElement('li');
                    imgPreview.classList.add('image-preview');
                    imgPreview.style.backgroundImage = 'url(' + e.target.result + ')';

                    // 새 파일의 순서를 임시로 저장합니다.
                    newFileOrders.push({element: imgPreview, file: file});
                    resolve(); // Promise를 해결합니다.
                };
            });

            promises.push(promise); // Promise 배열에 추가합니다.
            reader.readAsDataURL(file); // 파일을 읽어 Data URL로 변환합니다.
        }

        // 모든 Promise가 완료되면 실행됩니다.
        Promise.all(promises).then(() => {
            // 파일 이름으로 정렬합니다.
            newFileOrders.sort((a, b) => {
                return a.file.name.localeCompare(b.file.name);
            });
            // 정렬된 순서에 따라 data-order 속성을 설정하고 DOM에 삽입합니다.
            newFileOrders.forEach((item, index) => {
                item.element.setAttribute('data-order', collectedFiles.length - files.length + index + 1);
                dragDropArea.insertBefore(item.element, dragDropArea.querySelector('.add-image-placeholder'));
            });
            updateImageOrder(); // 이미지 순서를 업데이트합니다.

            // 로딩 창을 숨깁니다.
            document.getElementById('loading').style.display = 'none';
        });

        updateFileInput(); // 파일 입력을 업데이트합니다.
    }


    // 파일 입력을 업데이트하는 함수입니다.
    function updateFileInput() {
        let dataTransfer = new DataTransfer(); // DataTransfer 객체를 생성합니다.
        // 각 파일을 DataTransfer 객체에 추가합니다.
        for (let file of collectedFiles) {
            dataTransfer.items.add(file);
        }
        fileInput.files = dataTransfer.files; // 파일 입력의 files 속성을 업데이트합니다.
    }

    // 이미지 순서를 업데이트하는 함수입니다.
    function updateImageOrder() {
        console.log('Updating image order...'); // 콘솔에 로그를 출력합니다.
        const imageOrder = []; // 이미지 순서를 저장할 배열을 초기화합니다.
        const imagePreviews = dragDropArea.querySelectorAll('.image-preview'); // 모든 이미지 미리보기를 선택합니다.
        const imageOrdersInput = document.getElementById('imageOrders'); // 순서를 저장할 입력 필드를 선택합니다.

        // 이미지 미리보기가 있을 경우 순서를 업데이트합니다.
        if (imagePreviews.length > 0) {
            imagePreviews.forEach((img) => {
                // 이미 설정된 data-order 값을 사용합니다.
                imageOrder.push(img.getAttribute('data-order'));
            });
            imageOrdersInput.value = imageOrder.join(','); // 배열을 문자열로 변환하여 입력 필드에 저장합니다.
            console.log('Updated image order:', imageOrder.join(',')); // 콘솔에 로그를 출력합니다.
        }
    }

    // Sortable.js 라이브러리를 사용하여 dragDropArea를 드래그 앤 드롭 가능하게 만듭니다.
    new Sortable(dragDropArea, {
        animation: 0, // 애니메이션 지속 시간입니다.
        filter: '.add-image-placeholder', // 필터링할 요소의 클래스입니다.
        preventOnFilter: false, // 필터링된 요소가 preventDefault를 호출하지 않도록 설정합니다.
        onEnd: function() { // 드래그 앤 드롭이 끝났을 때 호출될 함수입니다.
            console.log('Drag and drop operation ended.'); // 콘솔에 로그를 출력합니다.
            updateImageOrder(); // 이미지 순서를 업데이트합니다.
        }
    });

    // 폼이 제출될 때 실행되는 이벤트 리스너를 추가합니다.
    document.querySelector('form').addEventListener('submit', function(e) {
        const imageOrdersInput = document.getElementById('imageOrders'); // 순서를 저장할 입력 필드를 선택합니다.
        console.log("Submitting image orders: ", imageOrdersInput.value); // 콘솔에 로그를 출력합니다.
        if (!imageOrdersInput.value) {
            // 입력 값이 없을 경우 기본값이나 다른 동작을 수행할 수 있습니다.
        }
    });

});
