let map;

function drawThumnail(input) {
    if (input.files && input.files[0]) {
        // 현재 썸네일 이미지가 몇개인지 가져옴
        let count = $('.slide-img').length;
        let imgLimit = 6 - count;
        let loopLimit = input.files.length > imgLimit ? imgLimit : input.files.length;

        // 6 - (썸네일 이미지 개수) 만큼 반복 돌면서 썸네일 이미지 추가
        for(let i = 0; i < loopLimit; i++) {
            let reader = new FileReader();
            reader.onload = function (e) {
                let slideImg = document.createElement('img'); // 썸네일 이미지
                slideImg.setAttribute('class', 'slide-img cursor');
                slideImg.setAttribute('style', 'background-image: url('+ e.target.result +');');
                slideImg.onclick = function(e){
                    alert('이미지 클릭');
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

function crudSubmit(){
    $('#crudForm').submit();
}

var infowindow = new naver.maps.InfoWindow();
function onSuccessGeolocation(position) {
    var location = new naver.maps.LatLng(position.coords.latitude,
                                         position.coords.longitude);

    map.setCenter(location); // 얻은 좌표를 지도의 중심으로 설정합니다.
    map.setZoom(18); // 지도의 줌 레벨을 변경합니다.
}

function onErrorGeolocation() {
    var center = map.getCenter();

    infowindow.setContent('<div style="padding:20px;">' +
        '<h5 style="margin-bottom:5px;color:#f00;">Geolocation failed!</h5>'+ "latitude: "+ center.lat() +"<br />longitude: "+ center.lng() +'</div>');

    infowindow.open(map, center);
}

function searchAddressToCoordinate(address) {
  naver.maps.Service.geocode({
    query: address
  }, function(status, response) {
    if (status === naver.maps.Service.Status.ERROR) {
      if (!address) {
        return alert('Geocode Error, Please check address');
      }
      return alert('Geocode Error, address:' + address);
    }

    if (response.v2.meta.totalCount === 0) {
      return alert('No result.');
    }

    var htmlAddresses = [],
      item = response.v2.addresses[0],
      point = new naver.maps.Point(item.x, item.y);

    if (item.roadAddress) {
      htmlAddresses.push('[도로명 주소] ' + item.roadAddress);
    }

    if (item.jibunAddress) {
      htmlAddresses.push('[지번 주소] ' + item.jibunAddress);
    }

    if (item.englishAddress) {
      htmlAddresses.push('[영문명 주소] ' + item.englishAddress);
    }

    infowindow.setContent([
      '<div style="padding:10px;min-width:200px;line-height:150%;">',
      '<h4 style="margin-top:5px;">검색 주소 : '+ address +'</h4><br />',
      htmlAddresses.join('<br />'),
      '</div>'
    ].join('\n'));

    map.setCenter(point);
    infowindow.open(map, point);
  });
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
        console.info('dialogDefinition : '  + dialogDefinition);
        console.log('dialog : '  + dialog);

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
        drawThumnail(this);
    });

    $('.column').click(function(){
        $('.img-input').eq(0).val('');
        $('.img-input').click();
    });

    $('#submitBtn').click(function() {
        crudSubmit();
    });

    //지도를 삽입할 HTML 요소 또는 HTML 요소의 id를 지정합니다.
    var mapDiv = document.getElementById('map'); // 'map'으로 선언해도 동일

    //옵션 없이 지도 객체를 생성하면 서울 시청을 중심으로 하는 16 레벨의 지도가 생성됩니다.
    map = new naver.maps.Map(mapDiv);

        if (navigator.geolocation) {
            /**
             * navigator.geolocation 은 Chrome 50 버젼 이후로 HTTP 환경에서 사용이 Deprecate 되어 HTTPS 환경에서만 사용 가능 합니다.
             * http://localhost 에서는 사용이 가능하며, 테스트 목적으로, Chrome 의 바로가기를 만들어서 아래와 같이 설정하면 접속은 가능합니다.
             * chrome.exe --unsafely-treat-insecure-origin-as-secure="http://example.com"
             */
            navigator.geolocation.getCurrentPosition(onSuccessGeolocation, onErrorGeolocation);
        } else {
            var center = map.getCenter();
            infowindow.setContent('<div style="padding:20px;"><h5 style="margin-bottom:5px;color:#f00;">Geolocation not supported</h5></div>');
            infowindow.open(map, center);
        }


  $('#address').on('keydown', function(e) {
    var keyCode = e.which;

    if (keyCode === 13) { // Enter Key
      searchAddressToCoordinate($('#address').val());
    }
  });

  $('#submit').on('click', function(e) {
    e.preventDefault();

    searchAddressToCoordinate($('#address').val());
  });
});