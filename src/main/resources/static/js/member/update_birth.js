// JavaScript 함수: 드롭다운 값을 실시간으로 입력칸에 반영
function updateBirthInput() {
    // 선택한 연도, 월, 일 가져오기
    const yearSelect = document.getElementById("birthYear");
    const monthSelect = document.getElementById("birthMonth");
    const daySelect = document.getElementById("birthDay");

    const selectedYear = yearSelect.value;
    const selectedMonth = monthSelect.value;
    const selectedDay = daySelect.value;

    // 두 자리 숫자로 만들기 위해 '0'을 추가
    const formattedMonth = selectedMonth.padStart(2, '0');
    const formattedDay = selectedDay.padStart(2, '0');

    // 생년월일 입력칸에 반영
    const birthInput = document.getElementById("memberBirth");
    birthInput.value = selectedYear + formattedMonth + formattedDay;
}

// JavaScript 함수: 생년월일 입력칸 클릭 시 드롭다운 표시 및 숨기기
function toggleBirthDropdowns() {
    const birthDropdowns = document.getElementById("birthDropdowns");
    if (birthDropdowns.style.display === "none" || birthDropdowns.style.display === "") {
        birthDropdowns.style.display = "block";
    } else {
        birthDropdowns.style.display = "none";
    }
}
