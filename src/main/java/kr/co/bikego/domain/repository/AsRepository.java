package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.AsEntity;
import kr.co.bikego.domain.entity.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AsRepository extends JpaRepository<AsEntity, Long>, JpaSpecificationExecutor<AsEntity> {
    AsEntity findBySeqAsAndPasswordAs(Long seqAs, String passwordAs);
}
