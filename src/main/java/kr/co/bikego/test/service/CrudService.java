package kr.co.bikego.test.service;

import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.repository.AttachRepository;
import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.service.AttachService;
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
    private AttachService attachService;

    @Transactional
    public Long savePost(CrudDto crudDto, String[] image, String[] imageName, String[] imageSize) {
        // 파일업로드가 필요할 때 Transactional 어노테이션 선언된 서비스 메소드에서
        List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "crud");
        Long crudId = 0l;
        if (attachEntities != null) {
            crudDto.setIdAttach(attachEntities.get(0).getAttachId()); //첨부파일 아이디 셋팅
            crudId = crudRepository.save(crudDto.toEntity()).getId();
        }
        return crudId;
    }

    public List<CrudDto> getCrudList() {
        List<CrudEntity> crudEntities = crudRepository.findAll();
        List<CrudDto> crudDtoList = new ArrayList<>();

        for(CrudEntity crudEntity : crudEntities) {
            CrudDto crudDto = CrudDto.builder()
                    .id(crudEntity.getId())
                    .title(crudEntity.getTitle())
                    .contents(crudEntity.getContents())
                    .idAttach(crudEntity.getAttach_id())
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
                .idAttach(crudEntity.getAttach_id())
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
