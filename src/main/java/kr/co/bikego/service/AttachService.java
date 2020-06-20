package kr.co.bikego.service;

import kr.co.bikego.PropertyConfig;
import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.repository.AttachRepository;
import kr.co.bikego.dto.AsDto;
import kr.co.bikego.dto.AttachDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@AllArgsConstructor
@Service
public class AttachService {
    private AttachRepository attachRepository;

    @Autowired
    private PropertyConfig propertyConfig;

    @Transactional
    public String saveAttachInfo(AttachDto attachDto) {
        return attachRepository.save(attachDto.toEntity()).getIdAttach();
    }

    /**
     * 첨부파일 단건조회
     * @param idAttach
     * @param snFileAttach
     * @return
     */
    public AttachDto getAttachInfo(String idAttach, int snFileAttach) {
        AttachEntity attachEntity = attachRepository.findByIdAttachAndSnFileAttach(idAttach, snFileAttach);
        AttachDto attachDto = AttachDto.builder()
                .idAttach(attachEntity.getIdAttach())
                .snFileAttach(attachEntity.getSnFileAttach())
                .nmOrgFileAttach(attachEntity.getNmOrgFileAttach())
                .nmSrvFileAttach(attachEntity.getNmSrvFileAttach())
                .pathFileAttach(attachEntity.getPathFileAttach())
                .sizeFileAttach(attachEntity.getSizeFileAttach())
                .ynDel(attachEntity.getYnDel())
                .registerAttach(attachEntity.getRegisterAttach())
                .regdtAttach(attachEntity.getRegdtAttach())
                .modifierAttach(attachEntity.getModifierAttach())
                .moddtAttach(attachEntity.getModdtAttach())
                .build();

        return attachDto;
    }

    public List<AttachDto> getAttachInfoList(String idAttach) {
        List<AttachEntity> attachEntities = attachRepository.findByIdAttachAndYnDel(idAttach, "N");

        List<AttachDto> attachDtoList = new ArrayList<>();

        for(AttachEntity attachEntity : attachEntities) {
            AttachDto attachDto = AttachDto.builder()
                    .idAttach(attachEntity.getIdAttach())
                    .snFileAttach(attachEntity.getSnFileAttach())
                    .nmOrgFileAttach(attachEntity.getNmOrgFileAttach())
                    .nmSrvFileAttach(attachEntity.getNmSrvFileAttach())
                    .pathFileAttach(attachEntity.getPathFileAttach())
                    .sizeFileAttach(attachEntity.getSizeFileAttach())
                    .ynDel(attachEntity.getYnDel())
                    .registerAttach(attachEntity.getRegisterAttach())
                    .regdtAttach(attachEntity.getRegdtAttach())
                    .modifierAttach(attachEntity.getModifierAttach())
                    .moddtAttach(attachEntity.getModdtAttach())
                    .build();

            attachDtoList.add(attachDto);
        }

        return attachDtoList;
    }

    /**
     *
     * @param srcImgPath
     * @param resizeWidth
     * @return
     * @throws Exception
     */
    public BufferedImage getResizeImg(String srcImgPath, int resizeWidth) throws Exception {

        Path path = Paths.get(srcImgPath);
        BufferedImage resizeImg = null;
        if(Files.isRegularFile(path)) {

            // 원본 이미지 read
            BufferedImage srcImg = ImageIO.read(Files.newInputStream(path));
            resizeImg = resizeImage(resizeWidth, srcImg);
//            resizeImg = srcImg;

        } else {
            throw new Exception("변환대상 이미지 파일이 존재하지 않습니다.("+path.getFileName()+")");
        }
        return resizeImg;
    }

    /**
     *
     * @param resizeWidth
     * @param srcImg
     * @return
     */
    public BufferedImage resizeImage(int resizeWidth, BufferedImage srcImg) {
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

    /**
     * 첨부파일그룹 신규
     * @param image
     * @param imageName
     * @param imageSize
     * @param type
     * @return
     */
    @Transactional
    public List<AttachEntity> saveImage(String[] image, String[] imageName, String[] imageSize, String type) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
        String randomValue = String.valueOf(10000 + new Random().nextInt(90000));
        String idAttach = time + "-" + type + "-"  + randomValue;

        return this.saveImage(image, imageName, imageSize, type, idAttach);
    }

    /**
     * 첨부파일그룹 수정
     * @param image
     * @param imageName
     * @param imageSize
     * @param type
     * @return
     */
    @Transactional
    public java.util.List<AttachEntity> saveImage(String[] image, String[] imageName, String[] imageSize, String type, String idAttach) {
        LocalDateTime localDateTime = LocalDateTime.now();
        // 첨부파일 DB저장 변수
        List<AttachEntity> attachEntities = new ArrayList<>();
        AttachDto attachDto = null;
        String extension = "", serverFileNm = "", serverFilePath = "";
        int lastSn = 0;
        AttachEntity lastAttach = attachRepository.findTopByIdAttachOrderBySnFileAttachDesc(idAttach);
        if(lastAttach != null) lastSn = lastAttach.getSnFileAttach();

        // 이미지 생성관련 변수
        String data = "";
        byte[] imageBytes = null;
        StringBuffer uploadPathBuf = new StringBuffer();
        uploadPathBuf.append(propertyConfig.getUploadPath());
        uploadPathBuf.append(propertyConfig.getFilePathSeperator() + type);
        uploadPathBuf.append(propertyConfig.getFilePathSeperator() + localDateTime.getYear());
        uploadPathBuf.append(propertyConfig.getFilePathSeperator() + localDateTime.getMonthValue());
        uploadPathBuf.append(propertyConfig.getFilePathSeperator() + localDateTime.getDayOfMonth());
        Path uploadPath = Paths.get(uploadPathBuf.toString());
//        Iterator<ImageWriter> iter = ImageIO.getImageWritersByMIMEType("image/jpeg");
//        ImageWriter imgWriter = iter.next();
//        ImageOutputStream imageOutputStream = null;

        if (Files.notExists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }

        try{
            for(int i=0; i<image.length; i++) {
                data = image[i];
                imageBytes = DatatypeConverter.parseBase64Binary(data);
                BufferedImage originImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
//                BufferedImage newImage = new BufferedImage(originImage.getWidth(), originImage.getHeight(), BufferedImage.TYPE_INT_RGB);
//                newImage.createGraphics().drawImage(originImage, 0, 0, Color.WHITE, null);

//                ImageWriteParam param = null;
//                if(Integer.valueOf(imageSize[i]) > 4000000) { //todo: 4메가 보다 크면 손실 압축
//                    param = imgWriter.getDefaultWriteParam();
//                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//                    param.setCompressionQuality(0.8F);
//                    param.setDestinationType(new ImageTypeSpecifier(newImage.getColorModel(), newImage.getSampleModel()));
//                }
                // 파일생성
                extension = imageName[i].substring(imageName[i].lastIndexOf("."), imageName[i].length()); // 확장자
                serverFileNm = UUID.randomUUID().toString() + System.currentTimeMillis() + extension; // 서버저장 파일명
                serverFilePath = uploadPathBuf.toString() + propertyConfig.getFilePathSeperator() + serverFileNm; // 서버저장 경로
                System.out.println("serverFilePath ;; " + serverFilePath);
//                imageOutputStream = ImageIO.createImageOutputStream(new FileOutputStream(serverFilePath));
//                imgWriter.setOutput(imageOutputStream);
//                imgWriter.write(null, new IIOImage(newImage, null, null), param);

                ImageIO.write(originImage, "jpg", new File(serverFilePath));

                // 첨부파일 저장 값
                attachDto = new AttachDto();
                attachDto.setIdAttach(idAttach);
                attachDto.setSnFileAttach(i+1+lastSn);
                attachDto.setNmOrgFileAttach(imageName[i]);
                attachDto.setNmSrvFileAttach(serverFileNm);
                attachDto.setPathFileAttach(serverFilePath);
                attachDto.setSizeFileAttach(Long.valueOf(imageSize[i]));
                attachDto.setExtendsAttach(extension);
                attachDto.setYnDel("N");
                attachDto.setRegisterAttach("test"); // todo: 사용자정보 저장?
                attachDto.setRegdtAttach(LocalDateTime.now());

                attachEntities.add(attachDto.toEntity());
            }

            attachRepository.saveAll(attachEntities);

//            imgWriter.dispose();
//            imageOutputStream.close();
            return attachEntities;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
            return null;
        } finally {
//            try{imgWriter.dispose();}catch(Exception e) {e.printStackTrace();}
//            try{imageOutputStream.close();}catch(Exception e) {e.printStackTrace();}
        }
    }

    @Transactional
    public void updateDelYn(AsDto asDto) {
        AttachDto attachDto = this.getAttachInfo(asDto.getIdAttach(), asDto.getSnFileAttach());
        attachDto.setYnDel("Y");
        attachDto.setModifierAttach(asDto.getWriterAs());
        attachDto.setModdtAttach(LocalDateTime.now());
        this.saveAttachInfo(attachDto);
    }

}
