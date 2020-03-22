package kr.co.bikego.test.service;

import kr.co.bikego.test.domain.entity.CrudEntity;
import kr.co.bikego.test.dto.CrudDto;
import lombok.AllArgsConstructor;
import kr.co.bikego.test.domain.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CrudService {
    private CrudRepository crudRepository;

    @Transactional
    public Long savePost(CrudDto crudDto) {
        return crudRepository.save(crudDto.toEntity()).getId();
    }

    public List<CrudDto> getCrudList() {
        List<CrudEntity> crudEntities = crudRepository.findAll();
        List<CrudDto> crudDtoList = new ArrayList<>();

        for(CrudEntity crudEntity : crudEntities) {
            CrudDto crudDto = CrudDto.builder()
                    .id(crudEntity.getId())
                    .title(crudEntity.getTitle())
                    .contents(crudEntity.getContents())
                    .id_attach(crudEntity.getId_attach())
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
