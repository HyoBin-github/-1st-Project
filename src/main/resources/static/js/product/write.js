document.addEventListener("DOMContentLoaded", function() {

    let queuedFiles = [];

    const imgPreviewBoxContainer = document.getElementById("image-preview-box-container");
    // const imgPreviewBox = document.getElementById("image-preview-box");

    var imageContainer = document.getElementById("image-container");


    // 마우스 관련 로직 =========================================================

    // imageContainer 위로 마우스가 올라가면 작동
    imageContainer.addEventListener("mouseover", (event) => {
      event.preventDefault();
      if (imgPreviewBoxContainer.childElementCount === 0) {
        imageContainer?.classList.add("imageContainer--hover");
      }
    });

    // imageContainer 밖으로 마우스가 나가면 작동
    imageContainer.addEventListener("mouseout", (event) => {
      event.preventDefault();
      imageContainer?.classList.remove("imageContainer--hover");
    });


    // 이미지 처리 관련 로직 ====================================================

    // 기본 dragover 이벤트 설정
    imgPreviewBoxContainer.addEventListener("dragover", function(event) {
      event.preventDefault();
    });

    // 기본 drop 이벤트 설정
    imgPreviewBoxContainer.addEventListener("drop", function(event) {
      event.preventDefault();
      const files = event.dataTransfer?.files;
      
      if (files && files.length > 0) {
        console.log("File drop operation.")
        const fileList = [...files].sort((a, b) => a.name.localeCompare(b.name));
        handleUpdate(fileList);
      }
    });

    // 클릭 이벤트 설정
    imgPreviewBoxContainer.addEventListener("click", function(event) {
      event.preventDefault();
      const fileInput = document.getElementById("fileInput");
      fileInput.click(); // 프로그래밍적으로 파일 입력을 클릭합니다.
    });

    // 파일 입력 변경 이벤트 설정
    document.getElementById("fileInput").addEventListener("change", function(event) {
      const files = event.target.files;
      if (files && files.length > 0) {
        console.log("File input operation.");
        const fileList = [...files].sort((a, b) => a.name.localeCompare(b.name));
        handleUpdate(fileList);
      }
    });




    // 비동기 함수로 선언하여 Promise와 async/await를 사용할 수 있게 합니다.
    async function handleUpdate(fileList) {
      // 이미지 미리보기를 할 컨테이너를 DOM에서 가져옵니다.
      const imgPreviewBoxContainer = document.getElementById("image-preview-box-container");
      
      queuedFiles = [...queuedFiles, ...Array.from(fileList)];

      
      // FileReader를 Promise로 감싸는 함수를 선언합니다.
      // 이 함수는 파일을 읽고 결과를 반환하는 Promise를 생성합니다.
      const loadFile = (file) => new Promise((resolve, reject) => {
        // FileReader 인스턴스를 생성합니다.
        const reader = new FileReader();
        
        // 파일 읽기가 성공적으로 완료되면 호출되는 이벤트 리스너를 추가합니다.
        reader.addEventListener("load", (event) => {
          // 읽기가 성공적으로 완료되면 resolve 함수를 호출하여 Promise를 완료합니다.
          // 파일과 읽은 결과를 객체로 묶어서 반환합니다.
          resolve({ file, result: event.target.result });
        });
        
        // 파일 읽기에 실패하면 호출되는 이벤트 리스너를 추가합니다.
        reader.addEventListener("error", reject);
        
        // 실제로 파일을 읽기 시작합니다. 데이터 URL 형식으로 읽습니다.
        reader.readAsDataURL(file);
      });
      
      // 모든 파일을 읽기 위한 Promise 배열을 생성합니다.
      // map 함수를 사용하여 각 파일에 대한 Promise를 생성하고 배열에 담습니다.
      const filePromises = fileList.map(loadFile);
      
      // Promise.all을 사용하여 모든 파일이 읽힐 때까지 기다립니다.
      // 이후 loadedFiles 배열에 각 파일과 그 결과가 담깁니다.
      const loadedFiles = await Promise.all(filePromises);
      
      // 모든 파일이 읽힌 후, 그 결과를 사용하여 DOM을 업데이트합니다.
      loadedFiles.forEach((loadedFile, index) => {

        // 미리보기 이미지를 생성합니다.
        const imgPreview = document.createElement("img");
        imgPreview.className = "image-preview";
        imgPreview.src = loadedFile.result;
        imgPreview.source = "internal";

        // 이미지를 담을 li 요소를 생성합니다.
        const imgPreviewBox = document.createElement("li");
        imgPreviewBox.className = "image-preview-box";
        imgPreviewBox.id = "draggable-container";

        // 생성한 li 요소에 이미지를 추가합니다.
        imgPreviewBox.appendChild(imgPreview);

        // 최종적으로 li 요소를 컨테이너에 추가합니다.
        imgPreviewBoxContainer.append(imgPreviewBox);
      });

      console.log(queuedFiles);
    }

    // 이미지 순서변경 로직 ======================================================

    // 배열 요소 교환 함수 (상대적인 순서)
    function moveElement(arr, oldIndex, newIndex) {
      const [movedElement] = arr.splice(oldIndex, 1);
      arr.splice(newIndex, 0, movedElement);
    }

    // sortable.js 라이브러리 사용. 이미지 순서 변경 기능.
    new Sortable(imgPreviewBoxContainer, {
      animation: 150, // 애니메이션 지속 시간.
      filter: ".imgPreviewBox", // 필터링할 요소.
      preventOnFilter: false, // 필터링된 요소가 preventDefault를 호출하지 않도록 설정.

      onStart: function(evt) {
        console.log("Drag and drop operation started.");;
      },
      onEnd: function(evt) { // 드래그 앤 드롭이 끝났을 때 호출될 함수.
        console.log("Drag and drop operation ended.");
      },
      onAdd: function(evt) {

      },
      onUpdate: function(evt) {
        // 배열에서 드래그 앤 드롭으로 순서가 변경된 요소의 인덱스를 찾아 위치를 바꿉니다.
        moveElement(queuedFiles, evt.oldIndex, evt.newIndex);
        console.log(queuedFiles);
        console.log("Item order changed.");
      }


    });

    document.getElementById("product-form").addEventListener("submit", async function(event) {
      event.preventDefault(); // 폼 제출 중지
  
      const formData = new FormData(event.target); // 폼 요소로부터 FormData 객체 생성
      formData.delete("fileInput"); // queuedFiles에 fileInput 이미지가 포함되어 있으므로 중복방지를 위해 삭제.

      // 이미지 파일 추가
      queuedFiles.forEach((file, index) => {
          formData.append("productImages", file);
      });
  
      // 서버로 데이터 전송
      const response = await fetch(event.target.action, {
          method: "POST",
          body: formData
      });
  
      if (response.ok) {
          const data = await response.json();
          if (data.status === "success"){
            console.log("Data sent successfully");
            window.location.href = data.redirectUrl; // 서버에서 받은 URL로 이동
          }
      } else {
          console.log("Error sending data");
      }


    });



});





