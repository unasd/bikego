package kr.co.bikego.service;

import kr.co.bikego.domain.entity.CsEntity;
import kr.co.bikego.domain.repository.CsRepository;
import kr.co.bikego.dto.CsDto;
import kr.co.bikego.dto.SearchDto;
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
public class CsService {
    @Autowired
    AES256Util aes;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private CsRepository csRepository;

    @Transactional
    public Long save(CsDto csDto) {
        Long seqFaq = csRepository.save(csDto.toEntity()).getSeqCs();
        return seqFaq;
    }

    public HashMap getList(SearchDto searchDto) throws Exception {
        HashMap result = new HashMap();
        List<CsEntity> entities = csRepository.findAll((Specification<CsEntity>) SearchSpec.searchLike3(searchDto));
        List<CsDto> dtoList = new ArrayList<>();

        for(CsEntity csEntity : entities) {
            CsDto csDto = CsDto.builder()
                    .seqCs(csEntity.getSeqCs())
                    .titleCs(csEntity.getTitleCs())
                    .contentsCs(csEntity.getContentsCs())
                    .replyCs(csEntity.getReplyCs())
                    .noCsTel(csEntity.getNoCsTel())
                    .emailCs(csEntity.getEmailCs())
                    .passwordCs(csEntity.getPasswordCs())
                    .writerCs(csEntity.getWriterCs())
                    .regdtCs(csEntity.getRegdtCs())
                    .modifierCs(csEntity.getModifierCs())
                    .moddtCs(csEntity.getModdtCs())
                    .ynDel(csEntity.getYnDel())
                    .ynReply(csEntity.getYnReply())
                    .categoryCs(csEntity.getCategoryCs())
                    .build();

            dtoList.add(csDto);
        }

        result.put("dtoList", dtoList);

        return result;
    }

    public HashMap getList(Pageable pageable, SearchDto searchDto) throws Exception {
        HashMap result = new HashMap();
        Page<CsEntity> entityPage = null;
        entityPage = csRepository.findAll((Specification<CsEntity>) SearchSpec.searchLike3(searchDto), pageable);

        List<CsEntity> entities = entityPage.getContent();
        List<CsDto> dtoList = new ArrayList<>();

        for(CsEntity csEntity : entities) {
            CsDto csDto = CsDto.builder()
                    .seqCs(csEntity.getSeqCs())
                    .titleCs(csEntity.getTitleCs())
                    .contentsCs(csEntity.getContentsCs())
                    .replyCs(csEntity.getReplyCs())
                    .noCsTel(csEntity.getNoCsTel())
                    .emailCs(csEntity.getEmailCs())
                    .passwordCs(csEntity.getPasswordCs())
                    .writerCs(csEntity.getWriterCs())
                    .regdtCs(csEntity.getRegdtCs())
                    .modifierCs(csEntity.getModifierCs())
                    .moddtCs(csEntity.getModdtCs())
                    .ynDel(csEntity.getYnDel())
                    .ynReply(csEntity.getYnReply())
                    .categoryCs(csEntity.getCategoryCs())
                    .build();

            dtoList.add(csDto);
        }

        result.put("entityPage", entityPage);
        result.put("dtoList", dtoList);

        return result;
    }

    public CsDto getDetail(Long seqCs) throws  Exception {
        Optional<CsEntity> entityWrapper = csRepository.findById(seqCs);
        CsEntity csEntity = entityWrapper.get();

        CsDto csDto = CsDto.builder()
                .seqCs(csEntity.getSeqCs())
                .titleCs(csEntity.getTitleCs())
                .contentsCs(csEntity.getContentsCs())
                .replyCs(csEntity.getReplyCs())
                .noCsTel(aes.decrypt(csEntity.getNoCsTel()))
                .emailCs(csEntity.getEmailCs())
                .passwordCs(csEntity.getPasswordCs())
                .writerCs(csEntity.getWriterCs())
                .regdtCs(csEntity.getRegdtCs())
                .modifierCs(csEntity.getModifierCs())
                .moddtCs(csEntity.getModdtCs())
                .ynDel(csEntity.getYnDel())
                .ynReply(csEntity.getYnReply())
                .categoryCs(csEntity.getCategoryCs())
                .build();

        return csDto;
    }

    @Transactional
    public Long updateNotice(CsDto csDto) {
        Long seqCs = csRepository.save(csDto.toEntity()).getSeqCs();
        return seqCs;
    }

    @Transactional
    public Long updateNoticeClient(CsDto csDto) throws Exception {
        CsDto oldData = this.getDetail(csDto.getSeqCs());
        csDto.setYnReply(oldData.getYnReply());
        csDto.setReplyCs(oldData.getReplyCs());
        Long seqCs = csRepository.save(csDto.toEntity()).getSeqCs();
        return seqCs;
    }

    @Transactional
    public void updateYnDel(Long seqCs) throws Exception {
        csRepository.updateYnDel(seqCs);
    }

    public boolean passwordChk(CsDto csDto) throws Exception {
        return passwordEncoder.matches(csDto.getPasswordCs(),
                Optional.ofNullable(this.getDetail(csDto.getSeqCs())).map(CsDto::getPasswordCs).orElse(""));
    }

    @Transactional
    public void delete(Long id) {
        csRepository.deleteById(id);
    }
}
