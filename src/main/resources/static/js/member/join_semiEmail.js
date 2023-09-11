//// 도메인 직접 입력 or domain option 선택
//const domainListEl = document.querySelector('#domainList')
//const domainInputEl = document.querySelector('#domainCustom')
//// select 옵션 변경 시
//domainListEl.addEventListener('change', (event) => {
//  // option에 있는 도메인 선택 시
//  if(event.target.value !== "custom") {
//    // 선택한 도메인을 input에 입력하고 disabled
//    domainInputEl.value = event.target.value
//    domainInputEl.disabled = true
//  } else { // 직접 입력 시
//    // input 내용 초기화 & 입력 가능하도록 변경
//    domainInputEl.value = ""
//    domainInputEl.disabled = false
//  }
//})
// 도메인 직접 입력 or domain option 선택
const domainListEl = document.querySelector('#domainList');
const domainInputEl = document.querySelector('#domainCustom');

// select 옵션 변경 시
domainListEl.addEventListener('change', (event) => {
  // option에 있는 도메인 선택 시
  if (event.target.value !== 'custom') {
    // 선택한 도메인을 input에 입력하고 disabled
    domainInputEl.value = event.target.value;
    domainInputEl.disabled = true;
  } else { // 직접 입력 시
    // input 내용 초기화 & 입력 가능하도록 변경
    domainInputEl.value = '';
    domainInputEl.disabled = false;
  }
});

// 이메일 입력 관련 요소들을 가져옴
const emailIdInput = document.querySelector('#emailId');
const domainCustomInput = document.querySelector('#domainCustom');
const domainListSelect = document.querySelector('#domainList');
const semiMemberEmailInput = document.querySelector('#semiMemberEmail');

// 이메일 입력 관련 요소들의 값이 변경될 때마다 호출되는 함수
function updateSemiMemberEmail() {
    const emailId = emailIdInput.value;
    const domainCustom = domainCustomInput.value;
    const selectedDomain = domainListSelect.value;

    if (selectedDomain === 'custom') {
        // 직접 입력 선택 시
        semiMemberEmailInput.value = emailId + '@' + domainCustom;
    } else {
        // 다른 옵션 선택 시
        semiMemberEmailInput.value = emailId + '@' + selectedDomain;
    }
}

// 값이 변경될 때마다 updateMemberEmail 함수 호출
emailIdInput.addEventListener('input', updateSemiMemberEmail);
domainCustomInput.addEventListener('input', updateSemiMemberEmail);
domainListSelect.addEventListener('change', updateSemiMemberEmail);

// 페이지 로드 시 초기 실행
updateSemiMemberEmail();