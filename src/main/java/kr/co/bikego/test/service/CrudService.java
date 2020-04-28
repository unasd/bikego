package kr.co.bikego.test.service;

import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.repository.AttachRepository;
import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.test.domain.entity.CrudEntity;
import kr.co.bikego.test.dto.CrudDto;
import lombok.AllArgsConstructor;
import kr.co.bikego.test.domain.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@AllArgsConstructor
@Service
public class CrudService {
    private CrudRepository crudRepository;
    private AttachRepository attachRepository;

    @Transactional
    public Long savePost(CrudDto crudDto, String[] image, String[] imageName, String[] imageSize) {
        // 파일업로드가 필요할 때 Transactional 어노테이션 선언된 서비스 메소드에서
        List<AttachEntity> attachEntities = saveImage(image, imageName, imageSize, "crud");
        Long crudId = 0l;
        if (attachEntities != null) {
            System.out.println("attachEntities :: " + attachEntities);
            attachRepository.saveAll(attachEntities);
            crudDto.setAttachId(attachEntities.get(0).getAttach_id()); //첨부파일 아이디 셋팅
            crudId = crudRepository.save(crudDto.toEntity()).getId();
        }
        return crudId;
    }

    @Transactional
    public List<AttachEntity> saveImage(String[] image, String[] imageName, String[] imageSize, String type) {
        // 첨부파일 DB저장 변수
        List<AttachEntity> attachEntities = new ArrayList<>();
        AttachDto attachDto = null;
        String extension = "", serverFileNm = "", serverFilePath = "";
        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
        String randomValue = String.valueOf(10000 + new Random().nextInt(90000));
        String idAttach = time + "_" + type + "_"  + randomValue;

        // 이미지 생성관련 변수
        String data = "";
        byte[] imageBytes = null;
        StringBuffer uploadPathBuf = new StringBuffer();
        uploadPathBuf.append("D:\\tweeks\\upload"); // todo: root 경로 properties 파일에서 받아와야 함
        uploadPathBuf.append("\\" + type);
        uploadPathBuf.append("\\" + localDateTime.getYear());
        uploadPathBuf.append("\\" + localDateTime.getMonthValue());
        uploadPathBuf.append("\\" + localDateTime.getDayOfMonth());
        Path uploadPath = Paths.get(uploadPathBuf.toString());
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByMIMEType("image/jpeg");
        ImageWriter imgWriter = iter.next();
        ImageOutputStream imageOutputStream = null;

        if (Files.notExists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try{
            for(int i=0; i<image.length; i++) {
//                System.out.println("image :: " + image[i]);
//                System.out.println("imageName :: " + imageName[i]);
//                System.out.println("imageSize :: " + imageSize[i]);
                data = image[i];
                imageBytes = DatatypeConverter.parseBase64Binary(data);
                BufferedImage originImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
                BufferedImage newImage = new BufferedImage(originImage.getWidth(), originImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                newImage.createGraphics().drawImage(originImage, 0, 0, Color.WHITE, null);

                ImageWriteParam param = null;
                if(Integer.valueOf(imageSize[i]) > 4000000) { //4메가 보다 크면 손실 압축
                    param = imgWriter.getDefaultWriteParam();
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(0.8F);
                    param.setDestinationType(new ImageTypeSpecifier(newImage.getColorModel(), newImage.getSampleModel()));
                }
                // 파일생성
                extension = imageName[i].substring(imageName[i].lastIndexOf("."), imageName[i].length()); // 확장자
                serverFileNm = UUID.randomUUID().toString() + System.currentTimeMillis() + extension; // 서버저장 파일명
                serverFilePath = uploadPathBuf.toString() + "\\" + serverFileNm; // 서버저장 경로
                imageOutputStream = ImageIO.createImageOutputStream(new FileOutputStream(serverFilePath));
                imgWriter.setOutput(imageOutputStream);
                imgWriter.write(null, new IIOImage(newImage, null, null), param);

                // 첨부파일 저장 값
                attachDto = new AttachDto();
                attachDto.setIdAttach(idAttach);
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
            return attachEntities;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try{imgWriter.dispose();}catch(Exception e) {}
            try{imageOutputStream.close();}catch(Exception e) {}
        }
    }

    public List<CrudDto> getCrudList() {
        List<CrudEntity> crudEntities = crudRepository.findAll();
        List<CrudDto> crudDtoList = new ArrayList<>();

        for(CrudEntity crudEntity : crudEntities) {
            CrudDto crudDto = CrudDto.builder()
                    .id(crudEntity.getId())
                    .title(crudEntity.getTitle())
                    .contents(crudEntity.getContents())
                    .id_attach(crudEntity.getAttach_id())
                    .id_writer(crudEntity.getId_writer())
                    .date_write(crudEntity.getDate_write())
                    .id_modifier(crudEntity.getId_modifier())
                    .date_modify(crudEntity.getDate_modify())
                    .build();

            crudDtoList.add(crudDto);
        }

        return crudDtoList;
    }

    @Transactional
    public CrudDto getPost(Long id) {
        Optional<CrudEntity> crudEntityWrapper = crudRepository.findById(id);
        CrudEntity crudEntity = crudEntityWrapper.get();

        CrudDto crudDTO = CrudDto.builder()
                .id(crudEntity.getId())
                .title(crudEntity.getTitle())
                .contents(crudEntity.getContents())
                .id_writer(crudEntity.getId_writer())
                .date_write(crudEntity.getDate_write())
                .build();

        return crudDTO;
    }

    @Transactional
    public void deletePost(Long id) {
        crudRepository.deleteById(id);
    }
}
