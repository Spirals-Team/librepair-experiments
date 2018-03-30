package com.codesquad.library.domain.exceptions;

import com.codesquad.library.domain.exceptions.model.BaseModelException;
import com.codesquad.library.domain.exceptions.security.BaseSecurityException;

import java.util.Arrays;

public enum ExceptionTypes {

    MODEL_RELATED(BaseModelException.class),
    SECURITY_RELATED(BaseSecurityException.class);

    private Class<? extends ApplicationException> metaClass;

    ExceptionTypes(Class<? extends ApplicationException> metaClass) {
        this.metaClass = metaClass;
    }

    public static ExceptionTypes findByExceptionMetaclass(Class<? extends ApplicationException> metaClass) {
        return Arrays.stream(ExceptionTypes.values()).filter(e -> e.isMatchingClass(metaClass)).findFirst().orElseThrow(() -> new NullPointerException("조건에 맞는 예외가 등록되어 있지 않습니다."));
    }

    public static ExceptionTypes findByInstance(ApplicationException exception) {
        return Arrays.stream(ExceptionTypes.values()).filter(e -> e.isMatchingInstance(exception)).findFirst().orElseThrow(() -> new NullPointerException("조건에 맞는 예외가 등록되어 있지 않습니다."));
    }

    private boolean isMatchingInstance(ApplicationException exception) {
        return exception.getClass().isAssignableFrom(this.metaClass);
    }

    private boolean isMatchingClass(Class<? extends ApplicationException> metaClass) {
        return metaClass.equals(this.metaClass);
    }
}
