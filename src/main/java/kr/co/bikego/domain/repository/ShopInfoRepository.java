package kr.co.bikego.domain.repository;

import kr.co.bikego.domain.entity.ShopInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ShopInfoRepository extends JpaRepository<ShopInfoEntity, Long>, JpaSpecificationExecutor<ShopInfoEntity> {
    @Transactional
    @Modifying
    @Query(value=" UPDATE shopinfo "
            + " SET del_yn = 'Y' "
            + " WHERE shop_seq = :seqShop ", nativeQuery = true)
    void updateYnDel(Long seqShop) throws Exception;
}
