package kr.co.bikego.util;

import kr.co.bikego.dto.SearchDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchSpec {
    public static Specification<?> searchLike(SearchDto searchDto) {
        return (Specification<?>) ((root, query, builder) ->
                builder.like(root.get(searchDto.getSearchType()), "%" + searchDto.getSearchKeyword() + "%")

        );
    }


    public static Specification<?> searchLike2(SearchDto searchDto,String delYn) {
        return (Specification<?>) ((root, query, builder) ->
                builder.equal(root.get("delYn"),delYn)
        );
    }

    public static Specification<?> searchLike3(SearchDto searchDto) {
        return (Specification<?>) ((root, query, builder) -> {
            List<Predicate> predicates = getPredicates(root, searchDto, builder);
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }


    public static List<Predicate> getPredicates(Root root, SearchDto searchDto, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        if(!Optional.ofNullable(searchDto.getSearchKeyword()).orElse("").isEmpty()) {
            predicates.add(builder.like(root.get(searchDto.getSearchType()), "%" + searchDto.getSearchKeyword() + "%"));
        }
        if(!Optional.ofNullable(searchDto.getYnDel()).orElse("").isEmpty()) {
            predicates.add(builder.equal(root.get("ynDel"), searchDto.getYnDel()));
        }

        return predicates;
    }

}