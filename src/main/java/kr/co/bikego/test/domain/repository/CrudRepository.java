package kr.co.bikego.test.domain.repository;

import kr.co.bikego.test.domain.entity.CrudEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudRepository extends JpaRepository<CrudEntity, Long> {
}
