package kr.co.bikego.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
@Service
public class ImageService {

    public BufferedImage getResizeImg(String srcImgPath, int resizeWidth) throws Exception {

        Path path = Paths.get(srcImgPath);
        BufferedImage resizeImg = null;
        ImageWriter imgWriter = null;
        if(Files.isRegularFile(path)) {

            try {
                // 원본 이미지 read
                BufferedImage srcImg = ImageIO.read(Files.newInputStream(path));

                if(resizeWidth == 0) {
                    return srcImg;
                } else {
                    int srcImgWidth = srcImg.getWidth(); // 비율 계산용
                    int srcImgHeight = srcImg.getHeight();// 비율 계산용

                    // 가로 기준으로 줄인다면
                    int resizeImgWidth = resizeWidth; // 줄이고 싶은 가로사이즈
                    int resizeImgHeight = (srcImgHeight*resizeImgWidth)/srcImgWidth; // 원본비율 대비 줄이는 세로사이즈

                    // 리사이즈 이미지 객체
                    resizeImg = new BufferedImage(resizeImgWidth, resizeImgHeight,BufferedImage.TYPE_INT_RGB);

                    // 원본 이미지를 리사이즈 객체에 그린다
                    Graphics2D g2 = resizeImg.createGraphics();
                    g2.drawImage(srcImg, 0, 0, resizeImgWidth, resizeImgHeight, null);
                    g2.dispose();

                    imgWriter = ImageIO.getImageWritersByFormatName("JPEG").next();
                    // 이미지 용량 줄이기
                    ImageWriteParam param = imgWriter.getDefaultWriteParam();
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(0.94F);
                    param.setDestinationType(new ImageTypeSpecifier(resizeImg.getColorModel(), resizeImg.getSampleModel()));

                    // 파일생성
                    //FileOutputStream resizeImgOutput = null;
                    //ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(new FileOutputStream("C:\\Users\\jsh\\Pictures\\SW1_0840_3.JPEG"));
                    //imgWriter.setOutput(imageOutputStream);

                    // 이미지 write : 파일을 줄여서 저장할 필요가 있을 때
                    //imgWriter.write(null, new IIOImage(resizeImg, null, null), null);

                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                try{imgWriter.dispose();}catch(Exception e) {}
            }

        } else {
            throw new Exception("변환대상 이미지 파일이 존재하지 않습니다.("+path.getFileName()+")");
        }
        return resizeImg;
    }
}
