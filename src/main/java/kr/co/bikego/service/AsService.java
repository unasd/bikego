package kr.co.bikego.service;

import kr.co.bikego.domain.entity.AsEntity;
import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.repository.AsRepository;
import kr.co.bikego.dto.AsDto;
import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.test.domain.entity.CrudEntity;
import kr.co.bikego.test.dto.CrudDto;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.SearchSpec;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AsService {
    @Autowired
    AES256Util aes;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private AsRepository asRepository;
    private AttachService attachService;

    @Transactional
    public Long saveAs(AsDto asDto, String[] image, String[] imageName, String[] imageSize) {
        if(image != null) {
            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "as");
            if (attachEntities != null) {
                asDto.setIdAttach(attachEntities.get(0).getIdAttach()); //첨부파일 아이디 셋팅
            }
        }
        Long seqAs = asRepository.save(asDto.toEntity()).getSeqAs();
        return seqAs;
    }

    public HashMap getAsList(Pageable pageble, SearchDto searchDto) throws Exception {
        HashMap result = new HashMap();
        Page<AsEntity> asEntityPage = null;
        asEntityPage = asRepository.findAll((Specification<AsEntity>) SearchSpec.searchLike3(searchDto), pageble);

        List<AsEntity> asEntities = asEntityPage.getContent();
        List<AsDto> asDtoList = new ArrayList<>();
        for(AsEntity asEntity : asEntities) {
            AsDto asDto = AsDto.builder()
                    .seqAs(asEntity.getSeqAs())
                    .titleAs(asEntity.getTitleAs())
                    .contentsAs(asEntity.getContentsAs())
                    .noTelAs(aes.decrypt(asEntity.getNoTelAs()))
                    .latitudeAs(asEntity.getLatitudeAs())
                    .longitudeAs(asEntity.getLongitudeAs())
                    .locationAs(asEntity.getLocationAs())
                    .dtlLocationAs(asEntity.getDtlLocationAs())
                    .passwordAs(asEntity.getPasswordAs())
                    .statAs(asEntity.getStatAs())
                    .commentAs(asEntity.getCommentAs())
                    .idAttach(asEntity.getIdAttach())
                    .writerAs(asEntity.getWriterAs())
                    .regdtAs(asEntity.getRegdtAs())
                    .modifierAs(asEntity.getModifierAs())
                    .moddtAs(asEntity.getModdtAs())
                    .ynDel(asEntity.getYnDel())
                    .build();

            asDtoList.add(asDto);
        }
        result.put("asEntityPage", asEntityPage);
        result.put("asDtoList", asDtoList);

        return result;
    }

    public boolean passwordChk(AsDto asDto) throws Exception {
        return passwordEncoder.matches(asDto.getPasswordAs(),
                Optional.ofNullable(this.getAsDetail(asDto.getSeqAs())).map(AsDto::getPasswordAs).orElse(""));
    }

    public AsDto getAsDetail(Long seqAs) throws Exception {
        Optional<AsEntity> asEntityWrapper = asRepository.findById(seqAs);
        AsEntity asEntity = asEntityWrapper.get();

        AsDto asDto = AsDto.builder()
                .seqAs(asEntity.getSeqAs())
                .titleAs(asEntity.getTitleAs())
                .contentsAs(asEntity.getContentsAs())
                .noTelAs(aes.decrypt(asEntity.getNoTelAs()))
                .latitudeAs(asEntity.getLatitudeAs())
                .longitudeAs(asEntity.getLongitudeAs())
                .locationAs(asEntity.getLocationAs())
                .dtlLocationAs(asEntity.getDtlLocationAs())
                .passwordAs(asEntity.getPasswordAs())
                .statAs(asEntity.getStatAs())
                .commentAs(asEntity.getCommentAs())
                .idAttach(asEntity.getIdAttach())
                .writerAs(asEntity.getWriterAs())
                .regdtAs(asEntity.getRegdtAs())
                .modifierAs(asEntity.getModifierAs())
                .moddtAs(asEntity.getModdtAs())
                .ynDel(asEntity.getYnDel())
                .build();

        return asDto;
    }

    @Transactional
    public Long updateAs(AsDto asDto, String[] image, String[] imageName, String[] imageSize) {
        if(image != null) {
            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "as", asDto.getIdAttach());
        }
        System.out.println("asDto ;; " + asDto);
        Long seqAs = asRepository.save(asDto.toEntity()).getSeqAs();

        return seqAs;
    }

    @Transactional
    public void updateYnDel(Long seqAs) throws Exception {
        asRepository.updateYnDel(seqAs);
    }
}
