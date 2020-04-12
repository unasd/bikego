package kr.co.bikego.test;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TestClass {

    public static void main(String[] args) throws Exception {
        /**
         * 첨부파일 ID 생성
         */
//        LocalDateTime localDateTime = LocalDateTime.now();
//        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
//        String type = "as";
//        String randomValue = String.valueOf(10000 + new Random().nextInt(90000));;
//        System.out.println(time + "_" + type + "_"  + randomValue);

        /**
         * 썸네일 이미지 생성
         */
        //Path path = Paths.get("C:\\Users\\jsh\\Pictures\\SW1_0840.JPG");
//
        //if(Files.isRegularFile(path)) {
        //    System.out.println("디렉토리 여부: " + Files.isDirectory(path));
        //    System.out.println("파일 여부: " + Files.isRegularFile(path));
        //    System.out.println("마지막 수정 시간: " + Files.getLastModifiedTime(path));
        //    System.out.println("파일 크기: " + Files.size(path));
        //    System.out.println("소유자: " + Files.getOwner(path));
        //    System.out.println("숨김 파일 여부: " + Files.isHidden(path));
        //    System.out.println("읽기 가능 여부: " + Files.isReadable(path));
        //    System.out.println("쓰기 가능 여부: " + Files.isWritable(path));
        //    System.out.println("파일명: " + path.getFileName());
        //    System.out.println("상위 디렉토리명: " + path.getParent());
//
        //    // 원본 이미지 read
        //    BufferedImage srcImg = ImageIO.read(Files.newInputStream(path));
        //    int srcImgWidth = srcImg.getWidth(); // 비율 계산용
        //    int srcImgHeight = srcImg.getHeight();// 비율 계산용
//
        //    // 가로 기준으로 줄인다면
        //    int resizeImgWidth = 1500; // 줄이고 싶은 가로사이즈
        //    int resizeImgHeight = (srcImgHeight*resizeImgWidth)/srcImgWidth; // 원본비율 대비 줄이는 세로사이즈
//
        //    // 리사이즈 이미지 객체
        //    BufferedImage resizeImg = new BufferedImage(resizeImgWidth, resizeImgHeight,BufferedImage.TYPE_INT_RGB);
//
        //    // 원본 이미지를 리사이즈 객체에 그린다
        //    Graphics2D g2 = resizeImg.createGraphics();
        //    g2.drawImage(srcImg, 0, 0, resizeImgWidth, resizeImgHeight, null);
        //    g2.dispose();
//
        //    ImageWriter imgWriter = ImageIO.getImageWritersByFormatName("JPEG").next();
        //    // 이미지 용량 줄이기
        //    ImageWriteParam param = imgWriter.getDefaultWriteParam();
        //    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        //    param.setCompressionQuality(0.94F);
        //    param.setDestinationType(new ImageTypeSpecifier(resizeImg.getColorModel(), resizeImg.getSampleModel()));
//
        //    // 파일생성
        //    FileOutputStream resizeImgOutput = null;
        //    ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(new FileOutputStream("C:\\Users\\jsh\\Pictures\\SW1_0840_3.JPEG"));
        //    imgWriter.setOutput(imageOutputStream);
//
        //    // 이미지 write
        //    imgWriter.write(null, new IIOImage(resizeImg, null, null), null);
        //} else {
        //    throw new Exception("변환대상 이미지 파일이 존재하지 않습니다.("+path.getFileName()+")");
        //}

        //InputStream in = getClass().getResourceAsStream("C:\\Users\\jsh\\Pictures\\SW1_0840.JPG");
        //System.out.println(in.read());
    }
}
