package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.AsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsRepository extends JpaRepository<AsEntity, Long> {
}
