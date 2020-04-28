package kr.co.bikego.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
@Service
public class ImageService {

    public BufferedImage getResizeImg(String srcImgPath, int resizeWidth) throws Exception {

        Path path = Paths.get(srcImgPath);
        BufferedImage resizeImg = null;
        if(Files.isRegularFile(path)) {

            // 원본 이미지 read
            BufferedImage srcImg = ImageIO.read(Files.newInputStream(path));
            resizeImg = resizeImage(resizeWidth, srcImg);

        } else {
            throw new Exception("변환대상 이미지 파일이 존재하지 않습니다.("+path.getFileName()+")");
        }
        return resizeImg;
    }

    private BufferedImage resizeImage(int resizeWidth, BufferedImage srcImg) {
        BufferedImage resizeImg;
        int srcImgWidth = srcImg.getWidth(); // 비율 계산용
        int srcImgHeight = srcImg.getHeight();// 비율 계산용

        // 가로 기준으로 줄인다면
        int resizeImgWidth = resizeWidth == 0 ? srcImgWidth : resizeWidth; // 줄이고 싶은 가로사이즈, 0이면 원본사이즈
        int resizeImgHeight = resizeWidth == 0 ? srcImgHeight : (srcImgHeight*resizeImgWidth)/srcImgWidth; // 원본비율 대비 줄이는 세로사이즈

        // 리사이즈 이미지 객체
        resizeImg = new BufferedImage(resizeImgWidth, resizeImgHeight,BufferedImage.TYPE_INT_RGB);

        // 원본 이미지를 리사이즈 객체에 그린다
        Graphics2D g2 = resizeImg.createGraphics();
        g2.drawImage(srcImg, 0, 0, resizeImgWidth, resizeImgHeight, null);
        g2.dispose();
        return resizeImg;
    }
}
