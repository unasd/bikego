package kr.co.bikego.domain.code;

public enum CsCategory {
    A("AS문의"),
    B("배송문의"),
    C("견적문의"),
    D("서비스"),
    E("파츠구매"),
    F("기타");

    final private String categoryName;

    private CsCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
