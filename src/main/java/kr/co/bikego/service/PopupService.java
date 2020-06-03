package kr.co.bikego.service;

import kr.co.bikego.domain.entity.AsEntity;
import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.entity.PopupinfoEntity;
import kr.co.bikego.domain.repository.PopupRepository;

import kr.co.bikego.dto.AsDto;
import kr.co.bikego.dto.PopupinfoDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.test.domain.entity.CrudEntity;
import kr.co.bikego.test.dto.CrudDto;
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
public class PopupService {
    private PopupRepository popupRepository;
    private AttachService attachService;


    public List<PopupinfoDto> getPopupList() {
        List<PopupinfoEntity> popupinfoEntities = popupRepository.findAll();
        List<PopupinfoDto> popupinfoDtoList = new ArrayList<>();

        for(PopupinfoEntity popupEntity : popupinfoEntities) {
            PopupinfoDto popupinfoDto = PopupinfoDto.builder()
                    .popupSeq(popupEntity.getPopupSeq())
                    .popupTitle(popupEntity.getPopupTitle())
                    .popupUrl(popupEntity.getPopupUrl())
                    .popupWriter(popupEntity.getPopupWriter())
                    .popupRegdt(popupEntity.getPopupRegdt())
                    .popYn(popupEntity.getPopYn())
                    .delYn(popupEntity.getDelYn())
                    .popupRegdt(popupEntity.getPopupRegdt())
                    .build();


            popupinfoDtoList.add(popupinfoDto);
        }

        return popupinfoDtoList;
    }


    public HashMap getPopupList(Pageable pageble, SearchDto searchDto) throws GeneralSecurityException, UnsupportedEncodingException {
        HashMap result = new HashMap();
        Page<PopupinfoEntity> popupinfoEntityPage = null;
        if(Optional.ofNullable(searchDto.getSearchKeyword()).orElse("").isEmpty()) {
            popupinfoEntityPage = popupRepository.findAll(pageble);
        } else {
            popupinfoEntityPage = popupRepository.findAll((Specification<PopupinfoEntity>) SearchSpec.searchLike(searchDto), pageble);
        }

        List<PopupinfoEntity> popupinfoEntities = popupinfoEntityPage.getContent();
        List<PopupinfoDto> popupinfoDtoList = new ArrayList<>();
        for(PopupinfoEntity popupEntity : popupinfoEntities) {
            PopupinfoDto popupinfoDto = PopupinfoDto.builder()
                    .popupSeq(popupEntity.getPopupSeq())
                    .popupTitle(popupEntity.getPopupTitle())
                    .popupUrl(popupEntity.getPopupUrl())
                    .popupWriter(popupEntity.getPopupWriter())
                    .popupRegdt(popupEntity.getPopupRegdt())
                    .popYn(popupEntity.getPopYn())
                    .delYn(popupEntity.getDelYn())
                    .popupRegdt(popupEntity.getPopupRegdt())
                    .build();

            popupinfoDtoList.add(popupinfoDto);
        }
        result.put("popupinfoEntityPage", popupinfoEntityPage);
        result.put("popupinfoDtoList", popupinfoDtoList);

        return result;
    }

    public Long savePost(PopupinfoDto popupinfoDto, String[] image, String[] imageName, String[] imageSize) {
        // 파일업로드가 필요할 때 Transactional 어노테이션 선언된 서비스 메소드에서
        System.out.println("111111==="+image);
        if(image != null) {
            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "crud");
            if (attachEntities != null) {
                popupinfoDto.setAttachId(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }
        Long crudId = popupRepository.save(popupinfoDto.toEntity()).getPopupSeq();
        return crudId;
    }


    @Transactional
    public PopupinfoDto getPost(Long popupSeq) {
        Optional<PopupinfoEntity> popupEntityWrapper = popupRepository.findById(popupSeq);
        PopupinfoEntity popupinfoEntity = popupEntityWrapper.get();

        PopupinfoDto popupinfoDto = PopupinfoDto.builder()
                .popupSeq(popupinfoEntity.getPopupSeq())
                .popupTitle(popupinfoEntity.getPopupTitle())
                .popupWriter(popupinfoEntity.getPopupWriter())
                .contents(popupinfoEntity.getContents())
                .attachId(popupinfoEntity.getAttachId())
                .delYn(popupinfoEntity.getDelYn())
                .popupUrl(popupinfoEntity.getPopupUrl())
                .popYn(popupinfoEntity.getPopYn())
                .popupRegdt(popupinfoEntity.getPopupRegdt())
                .build();

        return popupinfoDto;
    }

    @Transactional
    public void deletePost(Long popupSeq) {
        popupRepository.deleteById(popupSeq);
    }
}
