let kakaoMap; // 지도객체
let marker; // 마커
let ps = new kakao.maps.services.Places();; // 검색 서비스
let geocoder = new kakao.maps.services.Geocoder();// 주소-좌표 변환 객체를 생성합니다

function drawThumnail(input) {
    if (input.files && input.files[0]) {
        // 현재 썸네일 이미지가 몇개인지 가져옴
        let count = $('.slide-img').length;
        let imgLimit = $('.column').length - count;
        let loopLimit = input.files.length > imgLimit ? imgLimit : input.files.length;

        // 썸네일 이미지 개수 만큼 반복 돌면서 썸네일 이미지 추가
        for(let i = 0; i < loopLimit; i++) {
            let reader = new FileReader();
            reader.onload = function (e) {
                let slideImg = document.createElement('img'); // 썸네일 이미지
                slideImg.setAttribute('class', 'slide-img cursor');
                slideImg.setAttribute('style', 'background-image: url('+ e.target.result +');');
                slideImg.onclick = function(e){
                    $('#imageModal').modal('open');
                    e.stopPropagation();
                };
                $('.column').eq(count).html(slideImg);

                let removeIcon = document.createElement('i'); // 삭제버튼
                removeIcon.setAttribute('class', 'tiny material-icons img-remove-btn');
                removeIcon.append(document.createTextNode('remove_circle'));
                removeIcon.onclick = function(e){
                    // 삭제버튼 클릭
                    $(this).parent().remove();
                    let emptyColumn = document.createElement('div');
                    emptyColumn.setAttribute('class', 'column');
                    emptyColumn.onclick = function(e){
                        $('.img-input').click();
                    };
                    $('.img-row').append(emptyColumn);
                    e.stopPropagation();
                };
                $('.column').eq(count).append(removeIcon);

                let image = document.createElement('input'); // 서버로 전송할 이미지 데이터
                image.setAttribute('type', 'hidden');
                image.setAttribute('name', 'image');
                image.setAttribute('value', e.target.result.split(',')[1]);
                $('.column').eq(count).append(image);

                let imageName = document.createElement('input'); // 전송할 이미지명
                imageName.setAttribute('type', 'hidden');
                imageName.setAttribute('name', 'imageName');
                imageName.setAttribute('value', input.files[i].name);
                $('.column').eq(count).append(imageName);

                let imageSize = document.createElement('input'); // 전송할 이미지 크기
                imageSize.setAttribute('type', 'hidden');
                imageSize.setAttribute('name', 'imageSize');
                imageSize.setAttribute('value', input.files[i].size);
                $('.column').eq(count).append(imageSize);

                count++;
            }
            reader.readAsDataURL(input.files[i]);
        }
    }
}

function asSubmit(){
    $('#asForm').attr('method', 'post');
    $('#asForm').attr('action', '/as/post.do');
    $('#asForm').submit();
}

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {
    var keyword = document.getElementById('keyword').value;
    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        $('#alertModal').find('h4').text('');
        $('#alertModal').find('p').text('키워드를 입력하세요!');
        $('#alertModal').modal('open');
        return false;
    }
    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch( keyword, placesSearchCB);
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        var placePosition = new kakao.maps.LatLng(data[0].y, data[0].x);
        // 지도 중심을 이동 시킵니다
        kakaoMap.setCenter(placePosition);
        kakaoMap.setLevel(3);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
    }
}

function setCurrentLocation(e){
    if (navigator.geolocation) {
        /**
         * navigator.geolocation 은 Chrome 50 버젼 이후로 HTTP 환경에서 사용이 Deprecate 되어 HTTPS 환경에서만 사용 가능 합니다.
         * http://localhost 에서는 사용이 가능하며, 테스트 목적으로, Chrome 의 바로가기를 만들어서 아래와 같이 설정하면 접속은 가능합니다.
         * chrome.exe --unsafely-treat-insecure-origin-as-secure="http://example.com"
         */
        navigator.geolocation.getCurrentPosition(onSuccessGeolocation, onErrorGeolocation);
    } else {
        alert('현재위치 조회가 지원되지 않습니다.');
    }
}

function onSuccessGeolocation(position) {
    var currentPosition = new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude);
    // 지도 중심을 이동 시킵니다
    kakaoMap.setCenter(currentPosition);
//    marker.setPosition(currentPosition);
//todo: 마커 옮기기, 좌표 input 반영하기, 주소 input 반영하기
    kakaoMap.setLevel(3);
}

function onErrorGeolocation() {
   alert('현재위치 조회 오류입니다.');
}

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

$(document).ready(function(){
//    CKEDITOR.replace( 'contents',{
//        filebrowserUploadUrl: '/attach/ckImgUpload.do',
//        toolbarGroups: [
//            { name: 'editing',     groups: [ 'find', 'selection' ] },
//            { name: 'insert' },
//            { name: 'forms' },
//            { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] }
//        ],
//        image_previewText: ' '
//    });

    CKEDITOR.on('dialogDefinition', function (ev) {
        var dialogName = ev.data.name;
        var dialogDefinition = ev.data.definition;
        var dialog = ev.data.definition.dialog;

        if (dialogName == 'image') {
            dialog.on('show', function () {
                this.selectPage('Upload');
            });
            var infoTab = dialogDefinition.getContents( 'info' );
            infoTab.remove('txtBorder');
            infoTab.remove('txtHSpace');
            infoTab.remove('txtVSpace');
            infoTab.remove('txtWidth');
            infoTab.remove('txtHeight');
            infoTab.remove('txtAlt');
            infoTab.remove('cmbAlign');
            infoTab.remove('ratioLock');
        }
    });

    $('.img-input').change(function(){
        imgComn.drawThumnail(this);
    });

    $('.column, .imgUpBtn').click(function(){
        if($('.slide-img').length < $('.column').length) {
            $('.img-input').eq(0).val('');
            $('.img-input').click();
        } else {
            $('#alertModal').find('h4').text('');
            $('#alertModal').find('p').text('더 이상 추가할 수 없습니다.');
            $('#alertModal').modal('open');
        }
    });

    $('#submitBtn').click(function() {
        asSubmit();
    });

  var container = document.getElementById('kakaoMap');
  var options = { //지도를 생성할 때 필요한 기본 옵션
      center: new kakao.maps.LatLng(37.52983920869157, 126.99756873623262),
  	  level: 3 //지도의 레벨(확대, 축소 정도)
  };
  kakaoMap = new kakao.maps.Map(container, options);

    // 주소입력 input 엔터
    $('#keyword').on('keydown', function(e) {
      var keyCode = e.which;

      if (keyCode === 13) { // Enter Key
        e.preventDefault();
        e.stopPropagation();
        searchPlaces();
      }
    });

    // 주소검색 버튼
    $('#search').on('click', function(e) {
      e.preventDefault();
        searchPlaces();
    });

    // 현위치
    $('#here').on('click', function(e) {
        e.preventDefault();
        setCurrentLocation(e);
    });

    // 지도를 클릭한 위치에 표출할 마커
    marker = new kakao.maps.Marker({
        // 지도 중심좌표에 마커 생성
        position: kakaoMap.getCenter()
    });
    // 지도에 마커 표시
    marker.setMap(kakaoMap);
    //setCurrentLocation();

    // 지도 클릭 이벤트
    // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
    kakao.maps.event.addListener(kakaoMap, 'click', function(mouseEvent) {

        // 클릭한 위도, 경도 정보를 가져옵니다
        let latlng = mouseEvent.latLng;
        // 클릭한 위치로 마커 이동
        marker.setPosition(latlng);
        let latitudeAs = latlng.getLat();
        let longitudeAs = latlng.getLng();
        $('#latitudeAs').val(latitudeAs);
        $('#longitudeAs').val(longitudeAs);

        searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var detailAddr = result[0].road_address ? result[0].road_address.address_name : result[0].address.address_name;
                $('#locationAs').val(detailAddr);
                $("#locationAs").parent().find("label").addClass("active");
            }
        });
    });
});