package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.CsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CsRepository extends JpaRepository<CsEntity, Long>, JpaSpecificationExecutor<CsEntity> {
    @Transactional
    @Modifying
    @Query(value=" UPDATE cs_board "
            + " SET del_yn = 'Y' "
            + " WHERE cs_seq = :seqCs ", nativeQuery = true)
    void updateYnDel(Long seqCs) throws Exception;
}
