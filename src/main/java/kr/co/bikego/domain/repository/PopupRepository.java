package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.AsEntity;
import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.entity.PopupinfoEntity;
import kr.co.bikego.dto.SearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PopupRepository extends JpaRepository<PopupinfoEntity, Long> , JpaSpecificationExecutor<PopupinfoEntity> {
    Page<PopupinfoEntity> findAllBydelYn(String delYn, Pageable pageble , @Nullable Specification<PopupinfoEntity> spec);

    Page<PopupinfoEntity> findBydelYn(String delYn, Pageable pageble );

/*
    List<PopupinfoEntity> findBydelYnAndPopYn(String delYn,String popYn);
*/

    @Query("SELECT e FROM PopupinfoEntity e WHERE e.popupStartDt <= CURRENT_DATE AND  CURRENT_DATE <= e.popupEndDt")
    List<PopupinfoEntity> findBydelYnAndPopYn(String delYn,String popYn);

/*    @Query("select u from PopupinfoEntity u where delYn = 'N'")
    Stream<PopupinfoEntity> findAllByCustomQueryAndStream();*/


}



