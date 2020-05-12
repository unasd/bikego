package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.entity.AttachId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachRepository extends JpaRepository<AttachEntity, AttachId> {
    List<AttachEntity> findByIdAttach(String idAttach);

    AttachEntity findByIdAttachAndSnFileAttach(String idAttach, int snFileAttach);

    AttachEntity findTopByIdAttachOrderBySnFileAttachDesc(String idAttach);
}
