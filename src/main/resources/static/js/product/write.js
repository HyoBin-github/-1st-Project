document.addEventListener("DOMContentLoaded", function() {

    let queuedFiles = [];
    let prevImgTarget = null;  // 이전에 마우스가 올라간 타겟을 추적하기 위한 변수

    const imgPreviewBoxContainer = document.getElementById("image-preview-box-container");

    var imageContainer = document.getElementById("image-container");

    const colorRadioBtn = document.querySelectorAll(".colorRadioBtn");

    var productSubmit = document.getElementById("productSubmit");

    var imagePreview = document.getElementById("image-preview");

    var imagePreviewExpand = document.getElementById("imagePreviewExpand");

    
    // 이미지 업로드 시작 처리 관련 로직 =========================================

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

    // 클릭 이벤트 설정 (imagePreviewBoxContainer)
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


    // 이미지 처리 관련 로직 ====================================================
    

    let isFirstUpload = true;  // 첫 번째 이미지 첨부를 추적하기 위한 플래그 변수


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
        // imgPreview.id = "image-preview";
        imgPreview.src = loadedFile.result;
        imgPreview.source = "internal";

        // 이미지를 담을 li 요소를 생성합니다.
        const imgPreviewBox = document.createElement("li");
        imgPreviewBox.className = "image-preview-box";
        // imgPreviewBox.id = "draggable-container";

        // 생성한 li 요소에 이미지를 추가합니다.
        imgPreviewBox.appendChild(imgPreview);

        // 최종적으로 li 요소를 컨테이너에 추가합니다.
        imgPreviewBoxContainer.append(imgPreviewBox);

      });
      // 첫번째 이미지 업로드 시에만 실행되는 로직.
      if (isFirstUpload = true) {
        isFirstUpload = false;  // 플래그를 false로 설정하여 다시 실행되지 않게 합니다.
        imagePreviewExpandInit();
      }
      
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


    // 폼 제출 ===================================================================


    document.getElementById("product-form").addEventListener("submit", async function(event) {
      event.preventDefault(); // 폼 제출 중지
  
      const formData = new FormData(event.target); // 폼 요소로부터 FormData 객체 생성

      formData.delete("fileInput"); // queuedFiles에 fileInput 이미지가 포함되어 있으므로 중복방지를 위해 삭제.

      // productPrice 값을 가져옴.
      let productPrice = formData.get("productPrice");

      // productPrice 값에서 특수 문자와 원화 기호를 제거.
      if (productPrice) {
        productPrice = productPrice.replace(/[^0-9]/g, "");
        formData.set("productPrice", productPrice); // 수정된 값을 다시 설정합니다.
      }

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


    // 이미지 미리보기 큰 화면 ===================================================
    function imagePreviewExpandInit(){
      const imgPreviewInit = document.querySelector(".image-preview"); // 첫번째 요소만 부른다.
      const previewImageSrcInit = imgPreviewInit.getAttribute("src");

      imagePreviewExpand.src = previewImageSrcInit;
      imagePreviewExpand.className = "imagePreviewExpand" 

      imgPreviewInit.classList.add("--hover2");
      prevImgTarget = imgPreviewInit;
    }


    // 이미지 미리보기 큰 화면
    imgPreviewBoxContainer.addEventListener("mouseover", (event) => {
      event.preventDefault();
      
      if (event.target.source === "internal"){
        const previewImageSrc = event.target.getAttribute("src");

        if (imagePreviewExpand.src !== previewImageSrc) {    
          imagePreviewExpand.src = previewImageSrc;
          imagePreviewExpand.className = "imagePreviewExpand" 

          if (prevImgTarget != null) {
            prevImgTarget.classList.remove("--hover2");
          }

          event.target.classList.add("--hover2");

          prevImgTarget = event.target;
        }
      } 
    });


    // 기타 로직 ================================================================

    // imageContainer 위로 마우스가 올라가면 작동
    imageContainer.addEventListener("mouseover", (event) => {
      event.preventDefault();
      if (imgPreviewBoxContainer.childElementCount === 0) {
        imageContainer?.classList.add("--hover");
      }
    });

    // imageContainer 밖으로 마우스가 나가면 작동
    imageContainer.addEventListener("mouseout", (event) => {
      event.preventDefault();
      imageContainer?.classList.remove("--hover");
    });

    colorRadioBtn.forEach((colorRadioBtn) => {
      // 마우스가 위로 올라가면 작동
      colorRadioBtn.addEventListener("mouseover", (event) => {
        event.preventDefault();
        colorRadioBtn.classList.add("--hover");
      });
    
      // 마우스가 밖으로 나가면 작동
      colorRadioBtn.addEventListener("mouseout", (event) => {
        event.preventDefault();
        colorRadioBtn.classList.remove("--hover");
      });
    });

    // productSubmit 위로 마우스가 올라가면 작동
    productSubmit.addEventListener("mouseover", (event) => {
      event.preventDefault();
      productSubmit?.classList.add("--hover");
    });

    // productSubmit 밖으로 마우스가 나가면 작동
    productSubmit.addEventListener("mouseout", (event) => {
      event.preventDefault();
      productSubmit?.classList.remove("--hover");
    });

    // inputNumbersOnly 값을 가진 태그에 keydown 할 시 작동할 로직
    $(document).on("keydown", "input:text[inputNumbersOnly]", function(event) {
      // const strVal = $(this).val();
      const key = event.key;
      const allowedKeys = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
      "ArrowLeft", "ArrowRight", "Backspace", "Delete", "Tab"];
      
      if (!allowedKeys.includes(key)) {
          event.preventDefault();
      }
    });
    
    // koreanCurrency 값을 가진 태그를 focus out 할 시 작동할 로직
    $(document).on("focusout", "input:text[koreanCurrency]", function()	{
      $(this).val( $(this).val().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,") );
      // 만약 값이 있다면, 
      if($(this).val() != "" ) {
        // 원화(￦) 표시.
        $(this).val( $(this).val()+"￦");
      }		
    });
    
    // koreanCurrency 값을 가진 태그를 focus 할 시 작동할 로직
    $(document).on("focus", "input:text[koreanCurrency]", function()	{	
      $(this).val( $(this).val().replace("￦", ""));
    });



    // 브랜드명 자동완성 =========================================================

    let suggestions = [];
    let currentFocus; // 현재 포커스된 li 인덱스
    const autocompleteList = document.getElementById("autocomplete-list");
    const ulElement = document.getElementById("autocomplete-text-ul");
    const inputElement = document.getElementById("productBrandName");


    $(inputElement).keyup(function(e) {
        let keyword = $(this).val();

        // 키보드 위/아래 화살표 키는 무시.
        const ignoredKeys = ["ArrowUp", "ArrowDown"];
      
        if (ignoredKeys.includes(e.key)) {
            return
        }

        if (keyword.length >= 1) {
          console.log("About to make AJAX call with keyword: ", keyword);
          $.get("/search", { keyword: keyword })

          .done(function(data) {
            console.log("AJAX call succeeded with data: ", data);
            suggestions = data.map(item => item.productBrandName);
            updateAutocompleteList(suggestions);
          })
          
          .fail(function(jqXHR, textStatus, errorThrown) {
            console.log("AJAX call failed with jqXHR: ", jqXHR, " status: ", textStatus, " and error: ", errorThrown);
            console.log("HTTP Status Code: ", jqXHR.status);
            console.error("Error: ", textStatus, errorThrown);
          });
        } else if (keyword.length < 1) {
          autocompleteList.classList.add("--displayFalse");
        }
    });
  

    function updateAutocompleteList(suggestions) {
      console.log("Updating autocomplete with: ", suggestions);
  
      // 기존 목록을 비운다
      ulElement.innerHTML = "";
      autocompleteList.innerHTML = "";
      autocompleteList.classList.remove("--displayFalse");
      currentFocus = 0;
      

      // 만약 제안이 없다면,
      if (suggestions.length === 0) {
        const liElement = document.createElement("li");
        liElement.className = "autocomplete-text-li";
        liElement.textContent = "새로운 브랜드 명을 추가합니다.";
        ulElement.appendChild(liElement);
      // 그 외에는,
      } else {
        // 각각의 제안에 대해 li 엘리먼트를 생성하고 ul에 추가한다
        for (let i = 0; i < suggestions.length; i++) {
          const liElement = document.createElement("li");
          liElement.className = "autocomplete-text-li";
          liElement.textContent = suggestions[i];
          ulElement.appendChild(liElement);

          // 첫 번째 li에만 'autocomplete-active' 클래스를 추가
          if (i === 0) {
            liElement.classList.add("autocomplete-active");
          }
        }
      }

      // 생성한 ul 엘리먼트를 #autocomplete-list에 추가한다
      autocompleteList.appendChild(ulElement);
    }

    // 키보드 이벤트 리스너 추가
    $(inputElement).keydown(function(e) {
      let items = ulElement.getElementsByTagName("li");

      if (suggestions.length >= 1){

        if (e.keyCode === 40) { // 아래 화살표
          currentFocus++;
          addActive(items);
        } else if (e.keyCode === 38) { // 위 화살표
          currentFocus--;
          addActive(items);
        } else if (e.keyCode === 13) { // Enter 키
          e.preventDefault();
          inputElement.value = items[currentFocus].textContent; // inputElement에 해당 item 텍스트를 삽입.
          inputElement.blur();  // 포커스 아웃 시키기.
        }
      }
    });

    function addActive(items) {
      if (!items) return false;
      removeActive(items);
      if (currentFocus >= items.length) currentFocus = 0;
      if (currentFocus < 0) currentFocus = (items.length - 1);
      items[currentFocus].classList.add("autocomplete-active");
    }

    function removeActive(items) {
      for (let i = 0; i < items.length; i++) {
          items[i].classList.remove("autocomplete-active");
      }
    }

    $("#productBrandName").focusout(function() {
      autocompleteList.classList.add("--displayFalse");
    });


});





