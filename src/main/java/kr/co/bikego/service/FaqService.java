package kr.co.bikego.service;

import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.entity.FaqEntity;
import kr.co.bikego.domain.entity.NoticeEntity;
import kr.co.bikego.domain.repository.FaqRepository;
import kr.co.bikego.dto.FaqDto;
import kr.co.bikego.dto.NoticeDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.test.dto.CrudDto;
import kr.co.bikego.util.SearchSpec;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FaqService {
    private FaqRepository faqRepository;
    private AttachService attachService;

    @Transactional
    public Long save(FaqDto faqDto, String[] image, String[] imageName, String[] imageSize) {
        if(image != null) {
            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "faq");
            if (attachEntities != null) {
                faqDto.setIdAttach(attachEntities.get(0).getIdAttach());
            }
        }

        Long seqFaq = faqRepository.save(faqDto.toEntity()).getSeqFaq();
        return seqFaq;
    }

    public HashMap getList(SearchDto searchDto) throws Exception {
        HashMap result = new HashMap();
        List<FaqEntity> entities = faqRepository.findAll((Specification<FaqEntity>) SearchSpec.searchLike3(searchDto));
        List<FaqDto> dtoList = new ArrayList<>();

        for(FaqEntity faqEntity : entities) {
            FaqDto faqDto = FaqDto.builder()
                    .seqFaq(faqEntity.getSeqFaq())
                    .titleFaq(faqEntity.getTitleFaq())
                    .contentsFaq(faqEntity.getContentsFaq())
                    .idAttach(faqEntity.getIdAttach())
                    .writerFaq(faqEntity.getWriterFaq())
                    .regdtFaq(faqEntity.getRegdtFaq())
                    .modifierFaq(faqEntity.getModifierFaq())
                    .moddtFaq(faqEntity.getModdtFaq())
                    .ynDel(faqEntity.getYnDel())
                    .build();

            dtoList.add(faqDto);
        }

        result.put("dtoList", dtoList);

        return result;
    }

    public HashMap getList(Pageable pageable, SearchDto searchDto) throws Exception {
        HashMap result = new HashMap();
        Page<FaqEntity> entityPage = null;
        entityPage = faqRepository.findAll((Specification<FaqEntity>) SearchSpec.searchLike3(searchDto), pageable);

        List<FaqEntity> entities = entityPage.getContent();
        List<FaqDto> dtoList = new ArrayList<>();

        for(FaqEntity faqEntity : entities) {
            FaqDto faqDto = FaqDto.builder()
                    .seqFaq(faqEntity.getSeqFaq())
                    .titleFaq(faqEntity.getTitleFaq())
                    .contentsFaq(faqEntity.getContentsFaq())
                    .idAttach(faqEntity.getIdAttach())
                    .writerFaq(faqEntity.getWriterFaq())
                    .regdtFaq(faqEntity.getRegdtFaq())
                    .modifierFaq(faqEntity.getModifierFaq())
                    .moddtFaq(faqEntity.getModdtFaq())
                    .ynDel(faqEntity.getYnDel())
                    .build();

            dtoList.add(faqDto);
        }

        result.put("entityPage", entityPage);
        result.put("dtoList", dtoList);

        return result;
    }

    public FaqDto getDetail(Long seqFaq) throws  Exception {
        Optional<FaqEntity> entityWrapper = faqRepository.findById(seqFaq);
        FaqEntity faqEntity = entityWrapper.get();

        FaqDto faqDto = FaqDto.builder()
                .seqFaq(faqEntity.getSeqFaq())
                .titleFaq(faqEntity.getTitleFaq())
                .contentsFaq(faqEntity.getContentsFaq())
                .idAttach(faqEntity.getIdAttach())
                .writerFaq(faqEntity.getWriterFaq())
                .regdtFaq(faqEntity.getRegdtFaq())
                .modifierFaq(faqEntity.getModifierFaq())
                .moddtFaq(faqEntity.getModdtFaq())
                .ynDel(faqEntity.getYnDel())
                .build();

        return faqDto;
    }

    @Transactional
    public Long updateNotice(FaqDto faqDto, String[] image, String[] imageName, String[] imageSize) {
        if(image != null) {
            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "faq", faqDto.getIdAttach());
            if (attachEntities != null) {
                faqDto.setIdAttach(attachEntities.get(0).getIdAttach());
            }
        }

        Long seqFaq = faqRepository.save(faqDto.toEntity()).getSeqFaq();
        return seqFaq;
    }

    @Transactional
    public void updateYnDel(Long seqFaq) throws Exception {
        faqRepository.updateDelYn(seqFaq);
    }

    @Transactional
    public void delete(Long id) throws Exception {
        this.faqRepository.deleteById(id);
    }
}
