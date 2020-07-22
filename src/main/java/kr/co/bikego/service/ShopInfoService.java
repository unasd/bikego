package kr.co.bikego.service;

import kr.co.bikego.domain.entity.ShopInfoEntity;
import kr.co.bikego.domain.repository.ShopInfoRepository;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.dto.ShopInfoDto;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.SearchSpec;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ShopInfoService {
    @Autowired
    AES256Util aes;
    private ShopInfoRepository shopInfoRepository;

    @Transactional
    public Long save(ShopInfoDto shopInfoDto) {
        Long seqShop = shopInfoRepository.save(shopInfoDto.toEntity()).getSeqShop();
        return seqShop;
    }

    public HashMap getList(Pageable pageable, SearchDto searchDto) throws Exception {
        HashMap result = new HashMap();
        Page<ShopInfoEntity> entityPage = null;
        entityPage = shopInfoRepository.findAll((Specification<ShopInfoEntity>) SearchSpec.searchLike3(searchDto), pageable);

        List<ShopInfoEntity> entities = entityPage.getContent();
        List<ShopInfoDto> dtoList = new ArrayList<>();

        for(ShopInfoEntity shopInfoEntity : entities) {
            ShopInfoDto shopInfoDto = ShopInfoDto.builder()
                    .seqShop(shopInfoEntity.getSeqShop())
                    .nmShop(shopInfoEntity.getNmShop())
                    .addr1Shop(shopInfoEntity.getAddr1Shop())
                    .addr2Shop(shopInfoEntity.getAddr2Shop())
                    .noTelShop(aes.decrypt(shopInfoEntity.getNoTelShop()))
                    .latitudeShop(shopInfoEntity.getLatitudeShop())
                    .longitudeShop(shopInfoEntity.getLongitudeShop())
                    .commentShop(shopInfoEntity.getCommentShop())
                    .writerShop(shopInfoEntity.getWriterShop())
                    .regdtShop(shopInfoEntity.getRegdtShop())
                    .modifierShop(shopInfoEntity.getModifierShop())
                    .moddtShop(shopInfoEntity.getModdtShop())
                    .ynDel(shopInfoEntity.getYnDel())
                    .build();

            dtoList.add(shopInfoDto);
        }

        result.put("entityPage", entityPage);
        result.put("dtoList", dtoList);

        return result;
    }

    public ShopInfoDto getDetail(Long seqShop) throws  Exception {
        Optional<ShopInfoEntity> entityWrapper = shopInfoRepository.findById(seqShop);
        ShopInfoEntity shopInfoEntity = entityWrapper.get();

        ShopInfoDto shopInfoDto = ShopInfoDto.builder()
                .seqShop(shopInfoEntity.getSeqShop())
                .nmShop(shopInfoEntity.getNmShop())
                .addr1Shop(shopInfoEntity.getAddr1Shop())
                .addr2Shop(shopInfoEntity.getAddr2Shop())
                .noTelShop(aes.decrypt(shopInfoEntity.getNoTelShop()))
                .latitudeShop(shopInfoEntity.getLatitudeShop())
                .longitudeShop(shopInfoEntity.getLongitudeShop())
                .commentShop(shopInfoEntity.getCommentShop())
                .writerShop(shopInfoEntity.getWriterShop())
                .regdtShop(shopInfoEntity.getRegdtShop())
                .modifierShop(shopInfoEntity.getModifierShop())
                .moddtShop(shopInfoEntity.getModdtShop())
                .ynDel(shopInfoEntity.getYnDel())
                .build();

        return shopInfoDto;
    }

    @Transactional
    public Long updateShopInfo(ShopInfoDto shopInfoDto) {
        Long seqShop = shopInfoRepository.save(shopInfoDto.toEntity()).getSeqShop();
        return seqShop;
    }

    @Transactional
    public void updateYnDel(Long seqShop) throws Exception {
        shopInfoRepository.updateYnDel(seqShop);
    }
}
