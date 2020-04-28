package kr.co.bikego.service;

import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.entity.AttachId;
import kr.co.bikego.domain.repository.AttachRepository;
import kr.co.bikego.dto.AttachDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AttachService {
    private AttachRepository attachRepository;

    @Transactional
    public String saveAttachInfo(AttachDto attachDto) {
        return attachRepository.save(attachDto.toEntity()).getAttach_id();
    }

    public List<AttachDto> getAttachInfoList(AttachId attachId) {
        List<AttachEntity> attachEntities = attachRepository.findAllById((Iterable<AttachId>) attachId);

        List<AttachDto> attachDtoList = new ArrayList<>();

        for(AttachEntity attachEntity : attachEntities) {
            AttachDto attachDto = AttachDto.builder()
                    .attach_id(attachEntity.getAttach_id())
                    .attach_file_sn(attachEntity.getAttach_file_sn())
                    .attach_file_org_nm(attachEntity.getAttach_file_org_nm())
                    .attach_file_srv_nm(attachEntity.getAttach_file_srv_nm())
                    .attach_file_path(attachEntity.getAttach_file_path())
                    .attach_file_size(attachEntity.getAttach_file_size())
                    .del_yn(attachEntity.getDel_yn())
                    .attach_register(attachEntity.getAttach_register())
                    .attach_regdt(attachEntity.getAttach_regdt())
                    .attach_modifier(attachEntity.getAttach_modifier())
                    .attach_moddt(attachEntity.getAttach_moddt())
                    .build();

            attachDtoList.add(attachDto);
        }

        return attachDtoList;
    }

    public List<AttachDto> getAttachList() {
        List<AttachEntity> attachEntities = attachRepository.findAll();
        List<AttachDto> attachDtoList = new ArrayList<>();

        for(AttachEntity attachEntity : attachEntities) {
            AttachDto attachDto = AttachDto.builder()
                    .attach_id(attachEntity.getAttach_id())
                    .attach_file_sn(attachEntity.getAttach_file_sn())
                    .attach_file_org_nm(attachEntity.getAttach_file_org_nm())
                    .attach_file_srv_nm(attachEntity.getAttach_file_srv_nm())
                    .attach_file_path(attachEntity.getAttach_file_path())
                    .attach_file_size(attachEntity.getAttach_file_size())
                    .del_yn(attachEntity.getDel_yn())
                    .attach_register(attachEntity.getAttach_register())
                    .attach_regdt(attachEntity.getAttach_regdt())
                    .attach_modifier(attachEntity.getAttach_modifier())
                    .attach_moddt(attachEntity.getAttach_moddt())
                    .build();

            attachDtoList.add(attachDto);
        }

        return attachDtoList;
    }

}
