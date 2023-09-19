document.addEventListener("DOMContentLoaded", function() {
	

	const productListContainer = document.getElementById("product-list-container");
	const productList = document.getElementById('product-list');

	let searchQuery = "";
	
	let selectedFilters = {
		categories: [],
		brands: [],
		colors: []
	};

	let minProductId;
	let maxProductId;

	let isLoadingFirstTime = true; // 첫 로딩 감지용 플래그
	let isScrolledDownToBottom = false; // 스크롤이 바닥을 쳤는지 여부 감지용 플래그

	let limit = 10; // 초기 10개 로딩, 이후 8개 로딩.
	
	fetchAndDisplayProducts(lastProductId);  // 초기 상품 로딩
	
	// 상품 정보를 가져와서 화면에 표시하는 함수
	function fetchAndDisplayProducts(lastProductId) {
		
		const categoryQuery = selectedFilters.categories.join(',');
		const brandQuery = selectedFilters.brands.join(',');
  		const colorQuery = selectedFilters.colors.join(',');

  		const url = `/scrollList?lastProductId=${lastProductId}
								&limit=${limit}
								&searchKeyword=${searchQuery}
								&categories=${categoryQuery}
								&brands=${brandQuery}
								&colors=${colorQuery}`;
					
		console.log("Fetching from URL:", url);
		fetch(url)  // URL 수정
			.then(response => {
				return response.text(); // 일단 텍스트 형식으로 본문을 가져옵니다.
			})
			.then(bodyText => {
				console.log("Response body:", bodyText); // 본문을 출력합니다.
				return JSON.parse(bodyText); // 그 다음 JSON으로 파싱합니다.
			})
			.then(products => {
				console.log("Received products:", products);

				if(isLoadingFirstTime === true && products.length === 0){
					productListContainer.classList.add('--noResult');
					console.log("No Result")
				} else if (products.length === 0) {
					isScrolledDownToBottom = true;
					console.log("End of Result")
				} else {
					
					if(isLoadingFirstTime === true) {
						maxProductId = lastProductId;
					}
					
					productListContainer.classList.remove('--noResult');
					
					products.forEach(product => {
						const productElement = createProductElement(product);
						productList.appendChild(productElement);
					});

					if (products.length > 0) {
						products.sort((a, b) => a.id - b.id); 	// id를 기준으로 오름차순 정렬
						minProductId = products[0].id; // 첫 번째 요소의 id를 minProductId에 저장
					}

					// if (products.length < limit) {
					// 	lastProductId = 0;
					// }

				}

				
				console.log(lastProductId);

			})
			.catch(error => {
				console.error("Error fetching products:", error);
			});
	}

	// 이미지 배열에서 첫 번째 이미지 정보를 가져오는 함수
	function getFirstProductImage(product) {
		if (product.productImgDTOS && product.productImgDTOS.length > 0) {
			const imageName = product.productImgDTOS[0].productImgSavedName;
			return `/images/${imageName}`; // Spring Boot에서 설정한 경로를 사용
		} else {
			return '/images/product/iconmonstr-photo-camera-13.svg'; // 이미지가 없는 경우 기본 이미지 경로를 반환
		}
	}
	
	function isImageThenProduct(product) {
		if (product.productImgDTOS && product.productImgDTOS.length > 0) {
			return 'productImage';
		} else {
			return 'productImageAlt';
		}
	}

	function isImageThenCart(product) {
		if (product.productImgDTOS && product.productImgDTOS.length > 0) {
			return '--withImg';
		} else {
			return '--withoutImg';
		}
	}
	
	// 상품 정보를 HTML 요소로 변환하는 함수
	function createProductElement(product) {
		const productItemContainer = document.createElement('div');
		productItemContainer.className = 'productItemContainer';
		
		const productItemDiv = document.createElement('div');
		productItemDiv.className = 'productItemDiv';

		const productLink = document.createElement('a');
		productLink.href = `/product/${product.id}`;
		productLink.className = 'productLink';
		
		const productImageBox = document.createElement('div');
		productImageBox.className = "productImageBox";

		const productCartLink = document.createElement('a');
		productCartLink.href = `/cartLink/${product.id}`;
		productCartLink.className = 'productCartLink';

		const productCartImage = document.createElement('img');
		productCartImage.src = '/images/product/iconmonstr-shopping-cart-14.svg';
		productCartImage.classList.add('productCartImage');
		productCartImage.classList.add(isImageThenCart(product));


		const productImage = document.createElement('img');
		productImage.src = getFirstProductImage(product);
		productImage.classList.add(isImageThenProduct(product));
		

		const productId = document.createElement('span');
		productId.textContent = `${product.id}`;
		productId.classList.add("--displayFalse");

		const productColor = document.createElement('span');
		productColor.textContent = `${product.productColor}`;
		productColor.classList.add("--displayFalse");

		const productBrandName = document.createElement('div');
		productBrandName.textContent = `${product.productBrand.productBrandName}`;
		productBrandName.className = "productBrandName";

		const productName = document.createElement('div');
		productName.textContent = product.productName;
		productName.className = "productName";

		const productPrice = document.createElement('div');
		productPrice.textContent = `${product.productPrice} ￦`;
		productPrice.className = "productPrice";

		// 카트 관련 이동할 요소
		productCartLink.appendChild(productCartImage);
		productItemContainer.appendChild(productCartLink);

		// 이미지 관련 요소
		productImageBox.appendChild(productImage);

		// 요소들을 a 태그 안으로 이동
		productLink.appendChild(productImageBox);
		productLink.appendChild(productId);
		productLink.appendChild(productColor);
		productLink.appendChild(productBrandName);
		productLink.appendChild(productName);
		productLink.appendChild(productPrice);

		// productLink를 productDiv 안으로 이동
		productItemDiv.appendChild(productLink);
		// productItemDiv를 productItemContainer 안으로 이동
		productItemContainer.appendChild(productItemDiv);

		return productItemContainer;
	}


	let debounceTimer;	// 디바운싱 타이머

	// 스크롤이 페이지 끝에 도달하면 새로운 상품 로딩
	window.onscroll = function() {

		clearTimeout(debounceTimer);
		
		debounceTimer = setTimeout(function() {
			// 브라우저 창 상태에 따라 height에 변동이 있을 수 있기 때문에, -30만큼의 여유분을 주었다.
			if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 30) {
				if (isScrolledDownToBottom === false) {
					lastProductId = minProductId;
					if (isLoadingFirstTime === true) {
						limit = 8;
						isLoadingFirstTime = false
					}
					console.log("스크롤 기반 페이지 로딩");
					fetchAndDisplayProducts(lastProductId);
				}
			}
		
		}, 500);
	};





	// 검색 및 필터 ===============================================================

	document.getElementById("searchButton").addEventListener("click", function(e) {
		const searchKeyword = document.getElementById('searchKeyword');
		searchQuery = searchKeyword.value;
	
		// 기존 상품 목록을 지우기
		while (productList.firstChild) {
		productList.removeChild(productList.firstChild);
		}

		resetProductInfo();
		scrollToTop(); // 스크롤 초기화.

		// 검색어가 변경되면 상품을 다시 로딩
		fetchAndDisplayProducts(lastProductId);
	});
	
	document.getElementById("filters").addEventListener("change", function(e) {
		console.log("filter activated");
		const name = e.target.name;
		const value = e.target.value;
	
		if (e.target.checked) {
			selectedFilters[name].push(value);
		} else {
			const index = selectedFilters[name].indexOf(value);
			if (index > -1) {
			selectedFilters[name].splice(index, 1);
			}
		}
	
		// 기존 상품 목록을 지우기
		while (productList.firstChild) {
			productList.removeChild(productList.firstChild);
		}
	
		resetProductInfo();
		scrollToTop(); // 스크롤 초기화.

		// 필터가 변경되면 상품을 다시 로딩
		fetchAndDisplayProducts(lastProductId);
	});
	
	function resetProductInfo() {
		lastProductId = maxProductId;
		limit = 10;
		isLoadingFirstTime = true;
		isScrolledDownToBottom = false;
	}


	// 기타 로직 ===========================================================

	// 스크롤 초기화.
	function scrollToTop() {
		window.scrollTo({
			top: 0,
			behavior: 'smooth'
		});
	}
	
});


// 스크롤 초기화 함수 첫 로딩시.
function scrollToTopInit() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}

window.onload = function() {
	setTimeout(function() {
        scrollToTopInit();
    }, 100);  // 100ms 딜레이
};