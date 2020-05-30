package kr.co.bikego.domain.spec;

import kr.co.bikego.domain.entity.AsEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AsSpecs {

    public enum SearchKey {
        SCHTITLE("titleAs"),
        SCHCONTENTS("contentsAs"),
        SCHLOCATION("locationAs"),
        SCHWRITER("writerAs");

        private final String value;

        SearchKey(String value) {
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }

    public static Specification<AsEntity> searchWith(Map<SearchKey, Object> searchKeyword) {
        return (Specification<AsEntity>) ((root, query, builder) -> {
            List<Predicate> predicate = getPredicateWithKeyword(searchKeyword, root, builder);
            return builder.and(predicate.toArray(new Predicate[0]));
        });
    }

    private static List<Predicate> getPredicateWithKeyword(Map<SearchKey, Object> searchKeyword
                                                            , Root<AsEntity> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for(SearchKey key : searchKeyword.keySet()) {
            switch (key) {
                case SCHTITLE:
                case SCHCONTENTS:
                case SCHLOCATION:
                case SCHWRITER:
                    predicates.add(builder.like(
                       root.get(key.value).as(String.class), "%" +searchKeyword.get(key)+ "%"
                    ));
            }
        }
        return predicates;
    }

}
