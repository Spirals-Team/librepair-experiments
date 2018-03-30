package com.codesquad.library.domain.exceptions.model;

public class NoCategoryMatchingException extends BaseModelException {

    private static final String STD_ERR_MESSAGE = "문자열에 맞는 카테고리가 없습니다.";

    public NoCategoryMatchingException() {
        super(STD_ERR_MESSAGE);
    }

    public NoCategoryMatchingException(String message) {
        super(message);
    }
}
