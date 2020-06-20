$(document).ready(function(){
    imgComn = new imageCommon();
});

function imageCommon() {
    // 이미지 리사이즈(축소)
    this.resizeImage = function(imageObj) {
        let canvas = document.createElement("canvas"),
            max_size = 1280,
            // 최대 기준을 1280으로 잡음.
            width = imageObj.width,
            height = imageObj.height;

        if (width > height) {
            // 가로가 길 경우
            if (width > max_size) {
                height *= max_size / width;
                width = max_size;
            }
        } else {
            // 세로가 길 경우
            if (height > max_size) {
                width *= max_size / height;
                height = max_size;
            }
        }
        canvas.width = width;
        canvas.height = height;
        canvas.getContext("2d").drawImage(imageObj, 0, 0, width, height);
        let dataUrl = canvas.toDataURL("image/jpeg");
        return dataUrl;
    };

    // Blob파일 생성
    this.dataURLToBlob = function(dataURL){
        const BASE64_MARKER = ";base64,";

        // base64로 인코딩 되어있지 않을 경우
        if (dataURL.indexOf(BASE64_MARKER) === -1) {
            const parts = dataURL.split(",");
            const contentType = parts[0].split(":")[1];
            const raw = parts[1];
            return new Blob([raw], {
                type: contentType
            });
        }
        // base64로 인코딩 된 이진데이터일 경우
        let parts = dataURL.split(BASE64_MARKER);
        let contentType = parts[0].split(":")[1];
        let raw = window.atob(parts[1]);
        // atob()는 Base64를 디코딩하는 메서드
        let rawLength = raw.length;
        // 부호 없는 1byte 정수 배열을 생성
        let = new Uint8Array(rawLength); // 길이만 지정된 배열
        let i = 0;
        while (i < rawLength) {
            uInt8Array[i] = raw.charCodeAt(i);
            i++;
        }
        return new Blob([uInt8Array], {
            type: contentType
        });
    };

    // 썸네일, 파일업로드 정보 생성
    this.drawThumnail = function (input) {
        if (input.files && input.files[0]) {
            // 현재 썸네일 이미지가 몇개인지 가져옴
            let count = $('.slide-img').length;
            let imgLimit = $('.column').length - count;
            let loopLimit = input.files.length > imgLimit ? imgLimit : input.files.length;

            // 썸네일 이미지 개수 만큼 반복 돌면서 썸네일 이미지 추가
            for(let i = 0; i < loopLimit; i++) {
                let reader = new FileReader();
                reader.onload = function (e) {
                    // 리사이즈 할 이미지 객체 생성
                    let imageObj = new Image();
                    imageObj.src = e.target.result;
                    imageObj.onload = imageEvent => {

                        let dataURL = imgComn.resizeImage(imageObj); // 이미지 리사이즈

                        let slideImg = document.createElement('img'); // 썸네일 이미지
                        slideImg.setAttribute('class', 'slide-img cursor');
                        slideImg.setAttribute('style', 'background-image: url('+ dataURL +');');
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
                        image.setAttribute('value', dataURL.split(',')[1]); // 리사이즈
                        $('.column').eq(count).append(image);

                        let imageName = document.createElement('input'); // 전송할 이미지명
                        imageName.setAttribute('type', 'hidden');
                        imageName.setAttribute('name', 'imageName');
                        imageName.setAttribute('value', input.files[i].name);
                        $('.column').eq(count).append(imageName);

                        let imageSize = document.createElement('input'); // 전송할 이미지 크기
                        imageSize.setAttribute('type', 'hidden');
                        imageSize.setAttribute('name', 'imageSize');
                        imageSize.setAttribute('value', imgComn.stringToBytesFaster(dataURL.split(',')[1]).length);
                        $('.column').eq(count).append(imageSize);

                        if(i == loopLimit-1) { // 마지막 이미지를 크게 표시
                            imgComn.showSlides(count);
//                            slideIndex = count;
                        }
                        count++;
                    }
                }
                reader.readAsDataURL(input.files[i]);
            }
        }
    };

    // base64 이미지 용량산정
    this.stringToBytesFaster = function ( str ) {
        var ch, st, re = [], j=0;
        for (var i = 0; i < str.length; i++ ) {
            ch = str.charCodeAt(i);
            if(ch < 127)
            {
                re[j++] = ch & 0xFF;
            }
            else
            {
                st = [];    // clear stack
                do {
                    st.push( ch & 0xFF );  // push byte to stack
                    ch = ch >> 8;          // shift value down by 1 byte
                }
                while ( ch );
                // add stack contents to result
                // done because chars have "wrong" endianness
                st = st.reverse();
                for(var k=0;k<st.length; ++k)
                    re[j++] = st[k];
            }
        }
        // return an array of bytes
        return re;
    };

    // 슬라이드 이미지 표시
    this.showSlides = function(n) {
        let dots = $('.slide-img');
        if(dots.length > 0) {
            dots.removeClass('active');
            let imgSrc = dots.eq(n).css('background-image');
            if(typeof(imgSrc) != "undefined") {
                imgSrc = imgSrc.replace('url(','').replace(')','').replace(/\"/gi, "");
                $('.large-slide').eq(0).css({'background-image' : 'url('+ imgSrc +')'});
            }
            dots.eq(n).addClass('active');
            $('#numbertext').text((Number(n)+1) + '/ 6');
        }
    }


//    const slideIndex = 0;
//    plusSlides = function(n) {
//        slideIndex += n
//        // 이미지가 존재하는 범위내에 있는지 체크
//        if(slideIndex > 5) {
//            slideIndex = 0;
//        } else if(slideIndex < 0) {
//            slideIndex = 5;
//        }
//        showSlides(slideIndex);
//    }
}

function chkword(obj, maxByte) {

    var strValue = obj.value;
    var strLen = strValue.length;
    var totalByte = 0;
    var len = 0;
    var oneChar = "";
    var str2 = "";

    for (var i = 0; i < strLen; i++) {
        oneChar = strValue.charAt(i);
        if (escape(oneChar).length > 4) {
            totalByte += 2;
        } else {
            totalByte++;
        }

        // 입력한 문자 길이보다 넘치면 잘라내기 위해 저장
        if (totalByte <= maxByte) {
            len = i + 1;
        }
    }

    // 넘어가는 글자는 자른다.
    if (totalByte > maxByte) {
        alert(maxByte + "자를 초과 입력 할 수 없습니다.");
        str2 = strValue.substr(0, len);
        obj.value = str2;
    }
}