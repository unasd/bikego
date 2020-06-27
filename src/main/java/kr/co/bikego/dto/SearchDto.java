package kr.co.bikego.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchDto {
    private String searchType;
    private String searchKeyword;
    private String ynDel = "N";
}
