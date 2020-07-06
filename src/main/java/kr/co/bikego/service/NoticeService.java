package kr.co.bikego.service;

import kr.co.bikego.domain.entity.AsEntity;
import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.entity.NoticeEntity;
import kr.co.bikego.domain.repository.NoticeRepository;
import kr.co.bikego.dto.NoticeDto;
import kr.co.bikego.dto.SearchDto;
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
public class NoticeService {
    private NoticeRepository noticeRepository;
    private AttachService attachService;

    @Transactional
    public Long saveNotice(NoticeDto noticeDto, String[] image, String[] imageName, String[] imageSize) {
        if(image != null) {
            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "noti");
            if (attachEntities != null) {
                noticeDto.setIdAttach(attachEntities.get(0).getIdAttach());
            }
        }

        Long seqNoti = noticeRepository.save(noticeDto.toEntity()).getSeqNoti();
        return seqNoti;
    }

    public HashMap getNoticeList(Pageable pageable, SearchDto searchDto) throws Exception {
        HashMap result = new HashMap();
        Page<NoticeEntity> noticeEntityPage = null;
        noticeEntityPage = noticeRepository.findAll((Specification<NoticeEntity>) SearchSpec.searchLike3(searchDto), pageable);

        List<NoticeEntity> noticeEntities = noticeEntityPage.getContent();
        List<NoticeDto> noticeDtoList = new ArrayList<>();

        for(NoticeEntity noticeEntity : noticeEntities) {
            NoticeDto noticeDto = NoticeDto.builder()
                    .seqNoti(noticeEntity.getSeqNoti())
                    .keywordNoti(noticeEntity.getKeywordNoti())
                    .titleNoti(noticeEntity.getTitleNoti())
                    .contentsNoti(noticeEntity.getContentsNoti())
                    .idAttach(noticeEntity.getIdAttach())
                    .writerNoti(noticeEntity.getWriterNoti())
                    .regdtNoti(noticeEntity.getRegdtNoti())
                    .modifierNoti(noticeEntity.getModifierNoti())
                    .moddtNoti(noticeEntity.getModdtNoti())
                    .ynDel(noticeEntity.getYnDel())
                    .build();

            noticeDtoList.add(noticeDto);
        }

        result.put("noticeEntityPage", noticeEntityPage);
        result.put("noticeDtoList", noticeDtoList);

        return result;
    }

    public NoticeDto getNoticeDetail(Long seqNoti) throws  Exception {
        Optional<NoticeEntity> noticeEntityWrapper = noticeRepository.findById(seqNoti);
        NoticeEntity noticeEntity = noticeEntityWrapper.get();

        NoticeDto noticeDto = NoticeDto.builder()
                .seqNoti(noticeEntity.getSeqNoti())
                .keywordNoti(noticeEntity.getKeywordNoti())
                .titleNoti(noticeEntity.getTitleNoti())
                .contentsNoti(noticeEntity.getContentsNoti())
                .idAttach(noticeEntity.getIdAttach())
                .writerNoti(noticeEntity.getWriterNoti())
                .regdtNoti(noticeEntity.getRegdtNoti())
                .modifierNoti(noticeEntity.getModifierNoti())
                .moddtNoti(noticeEntity.getModdtNoti())
                .ynDel(noticeEntity.getYnDel())
                .build();

        return noticeDto;
    }

    @Transactional
    public Long updateNotice(NoticeDto noticeDto, String[] image, String[] imageName, String[] imageSize) {
        if(image != null) {
            List<AttachEntity> attachEntities = attachService.saveImage(image, imageName, imageSize, "noti", noticeDto.getIdAttach());
            if (attachEntities != null) {
                noticeDto.setIdAttach(attachEntities.get(0).getIdAttach());
            }
        }

        Long seqNotice = noticeRepository.save(noticeDto.toEntity()).getSeqNoti();
        return seqNotice;
    }

    @Transactional
    public void updateYnDel(Long seqNoti) throws Exception {
        noticeRepository.updateDelYn(seqNoti);
    }
}
