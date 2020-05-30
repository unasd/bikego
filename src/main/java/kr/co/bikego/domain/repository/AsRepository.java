package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.AsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AsRepository extends JpaRepository<AsEntity, Long>, JpaSpecificationExecutor<AsEntity> {
}
