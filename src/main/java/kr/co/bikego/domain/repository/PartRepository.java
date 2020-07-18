package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.PartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PartRepository extends JpaRepository<PartEntity, Long> , JpaSpecificationExecutor<PartEntity> {
    Page<PartEntity> findAllBydelYn(String delYn, Pageable pageble , @Nullable Specification<PartEntity> spec);

    Page<PartEntity> findBydelYn(String delYn, Pageable pageble );

/*
    List<PartEntity> findBydelYnAndPopYn(String delYn,String popYn);
*/

    @Query("SELECT e FROM PartEntity e WHERE e.partStartDt <= CURRENT_DATE AND  CURRENT_DATE <= e.partEndDt")
    List<PartEntity> findBydelYnAndPopYn(String delYn,String popYn);

/*    @Query("select u from PartEntity u where delYn = 'N'")
    Stream<PartEntity> findAllByCustomQueryAndStream();*/


}



