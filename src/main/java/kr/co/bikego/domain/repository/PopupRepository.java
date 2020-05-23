package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.PopupinfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopupRepository extends JpaRepository<PopupinfoEntity, Long> {
}



