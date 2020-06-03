package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.AsEntity;
import kr.co.bikego.domain.entity.PopupinfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PopupRepository extends JpaRepository<PopupinfoEntity, Long> , JpaSpecificationExecutor<PopupinfoEntity> {
}



