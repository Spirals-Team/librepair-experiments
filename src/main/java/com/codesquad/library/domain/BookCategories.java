package com.codesquad.library.domain;

import com.codesquad.library.domain.exceptions.model.NoCategoryMatchingException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BookCategories {

    SOFTWARE_ENGINEERING(0, "소프트웨어공학", "glyphicon glyphicon-education"),
    JAVA(1, "자바", "devicon-java-plain"),
    SWIFT(2, "스위프트", "devicon-swift-plain"),
    JAVASCRIPT(3, "자바스크립트", "devicon-javascript-plain"),
    DATABASE(4, "데이터베이스", "devicon-mysql-plain"),
    HTML_CSS(5, "HTML, CSS, 마크업", "devicon-html5-plain"),
    DEVELOPMENT_METHODOLOGIES(6, "개발방법론(xDD)", "glyphicon glyphicon-wrench"),
    HUMAN_COMPUTER_INTERACTION(7, "유저인터페이스, HCI", "glyphicon glyphicon-leaf"),
    GENERAL(8, "일반", "glyphicon glyphicon-tent"),
    BUSINESS(9, "경영, 창업", "glyphicon glyphicon-briefcase");

    private int code;
    private String iconTag;

    @JsonProperty("category")
    private String label;

    BookCategories(int code, String label, String iconTag) {
        this.code = code;
        this.label = label;
        this.iconTag = iconTag;
    }

    private boolean isMatchingLabel(String label) {
        return label.equals(this.label);
    }

    public static BookCategories findByLabel(String label) {
        return Arrays.stream(BookCategories.values()).filter(c -> c.isMatchingLabel(label)).findFirst().orElseThrow(() -> new NoCategoryMatchingException());
    }
}
