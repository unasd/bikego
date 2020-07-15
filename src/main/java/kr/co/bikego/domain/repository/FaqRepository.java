package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.FaqEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FaqRepository extends JpaRepository<FaqEntity, Long>, JpaSpecificationExecutor<FaqEntity> {

    @Transactional
    @Modifying
    @Query(value = " UPDATE faq_board " +
                      " SET del_yn = 'Y' " +
                    " WHERE faq_seq = :seqFaq ", nativeQuery = true)
    void updateDelYn(Long seqFaq) throws Exception;
}
