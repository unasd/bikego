package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>, JpaSpecificationExecutor<NoticeEntity> {
    @Transactional
    @Modifying
    @Query(value = " UPDATE noti_board " +
                     " SET del_yn = 'Y' " +
                   " WHERE noti_seq = :seqNoti ", nativeQuery = true)
    void updateDelYn(Long seqNoti) throws Exception;
}
