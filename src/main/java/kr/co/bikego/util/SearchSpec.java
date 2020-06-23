package kr.co.bikego.util;

import kr.co.bikego.dto.SearchDto;
import org.springframework.data.jpa.domain.Specification;

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


}