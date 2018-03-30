package com.codesquad.library.domain.exceptions;

import com.codesquad.library.domain.exceptions.model.BaseModelException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ErrorCodesTest {

    private ErrorCodes codes;

    @Before
    public void setUp() {
        this.codes = ErrorCodes.INTERNAL_ERROR;
    }

    @Test
    public void ERRORCODE_TEST() {
        assertThat(this.codes.getCode(), is(20));
    }

    @Test
    public void FIND_BY_CODE_TEST() {
        assertThat(ErrorCodes.findByCode(10), is(ErrorCodes.NOT_FOUND));
    }

    @Test
    public void ISASSIGNABLE_TEST() {
        ApplicationException e = new BaseModelException("fuck!");

        assertTrue(e.getClass().isAssignableFrom(BaseModelException.class));
    }

}