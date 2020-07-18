package kr.co.bikego.service;

import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.entity.PartEntity;
import kr.co.bikego.domain.repository.PartRepository;
import kr.co.bikego.dto.PartinfoDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.util.SearchSpec;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PartService {
    private PartRepository partRepository;
    private AttachService attachService;



    public HashMap getPartList(Pageable pageble, SearchDto searchDto) throws GeneralSecurityException, UnsupportedEncodingException {
        HashMap result = new HashMap();
        Page<PartEntity> PartEntityPage = null;
        if(Optional.ofNullable(searchDto.getSearchKeyword()).orElse("").isEmpty()) {
            PartEntityPage = partRepository.findBydelYn("N",pageble);


        } else {

            PartEntityPage = partRepository.findAll(((Specification<PartEntity>)  SearchSpec.searchLike(searchDto)).and((Specification<PartEntity>)SearchSpec.searchLike2(searchDto,"N")) , pageble);
     }

        List<PartEntity> partEntities = PartEntityPage.getContent();
        List<PartinfoDto> PartinfoDtoList = new ArrayList<>();
        for(PartEntity partEntity : partEntities) {
            PartinfoDto partinfoDto = PartinfoDto.builder()
                    .partSeq(partEntity.getPartSeq())
                    .partTitle(partEntity.getPartTitle())
                    .partUrl(partEntity.getPartUrl())
                    .partUrlWindow(partEntity.getPartUrlWindow())
                    .partWriter(partEntity.getPartWriter())
                    .partRegdt(partEntity.getPartRegdt())
                    .contents(partEntity.getContents())
                    .popYn(partEntity.getPopYn())
                    .delYn(partEntity.getDelYn())
                    .partStartDt(partEntity.getPartStartDt())
                    .partEndDt(partEntity.getPartEndDt())
                    .attachId(partEntity.getAttachId())

                    .build();

            PartinfoDtoList.add(partinfoDto);
        }
        result.put("partEntityPage", PartEntityPage);
        result.put("partinfoDtoList", PartinfoDtoList);

        return result;
    }

    public Long savePost(PartinfoDto PartinfoDto, String[] image, String[] imageName, String[] imageSize) {
        // 파일업로드가 필요할 때 Transactional 어노테이션 선언된 서비스 메소드에서

        if(image != null) {
            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "crud");
            if (attachEntities != null) {
                PartinfoDto.setAttachId(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }
        Long crudId = partRepository.save(PartinfoDto.toEntity()).getPartSeq();
        return crudId;
    }


    @Transactional
    public PartinfoDto getPost(Long partSeq) {
        Optional<PartEntity> partEntityWrapper = partRepository.findById(partSeq);
        PartEntity PartEntity = partEntityWrapper.get();

        PartinfoDto partinfoDto = PartinfoDto.builder()
                .partSeq(PartEntity.getPartSeq())
                .partTitle(PartEntity.getPartTitle())
                .partWriter(PartEntity.getPartWriter())
                .contents(PartEntity.getContents())
                .attachId(PartEntity.getAttachId())
                .delYn(PartEntity.getDelYn())
                .partUrl(PartEntity.getPartUrl())
                .partUrlWindow(PartEntity.getPartUrlWindow())
                .popYn(PartEntity.getPopYn())
                .partRegdt(PartEntity.getPartRegdt())
                .partStartDt(PartEntity.getPartStartDt())
                .partEndDt(PartEntity.getPartEndDt())
                .build();

        return partinfoDto;
    }

    @Transactional
    public void deletePost(Long partSeq) {
        partRepository.deleteById(partSeq);
    }
}
