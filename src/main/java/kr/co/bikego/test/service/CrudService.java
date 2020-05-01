package kr.co.bikego.test.service;

import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.test.domain.entity.CrudEntity;
import kr.co.bikego.test.dto.CrudDto;
import lombok.AllArgsConstructor;
import kr.co.bikego.test.domain.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(image != null) {
            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "crud");
            if (attachEntities != null) {
                crudDto.setIdAttach(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }
        Long crudId = crudRepository.save(crudDto.toEntity()).getId();
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
