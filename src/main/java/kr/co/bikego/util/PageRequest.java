package kr.co.bikego.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Optional;

@Getter
@Setter
public final class PageRequest {

    private int page = 1;
    private int listSize = 20;
    private int pageSize = 5;
    private Sort.Direction direction = Sort.Direction.DESC;
    private String sortProp;

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page - 1, listSize, direction, sortProp);
    }

    public HashMap pagination(Page pageParam) {
        HashMap pagingResult = new HashMap();
        int totalCount = Optional.ofNullable(pageParam.getTotalElements()).map(Long::intValue).orElse(0);
        int totalPage = Optional.ofNullable(pageParam.getTotalPages()).orElse(0);

        int currPageBlock = page / pageSize;
        if(page % pageSize > 0) currPageBlock++;

        int startPage = (currPageBlock - 1) * pageSize + 1;
        int endPage = 0;
        if(startPage + (pageSize-1) > totalPage) {
            endPage = totalPage;
        } else {
            endPage = startPage + (pageSize-1);
        }

        pagingResult.put("page", this.page);
        pagingResult.put("listSize", this.listSize);
        pagingResult.put("pageSize", this.pageSize);
        pagingResult.put("direction", this.direction);
        pagingResult.put("sortProp", this.sortProp);
        pagingResult.put("totalCount", totalCount);
        pagingResult.put("totalPage", totalPage);
        pagingResult.put("currPageBlock", currPageBlock);
        pagingResult.put("startPage", startPage);
        pagingResult.put("endPage", endPage);

        pagingResult.put("hasPrevious", pageParam.hasPrevious());
        pagingResult.put("hasNext", pageParam.hasNext());

        return pagingResult;
    }
}
