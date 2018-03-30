package com.codesquad.library.domain.exceptions;

import com.codesquad.library.domain.exceptions.model.BaseModelException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HttpResponseResourceTest {

    private ApplicationException exception;
    private HttpResponseResource responseResource;

    @Before
    public void setUp() {
        this.exception = new BaseModelException(ErrorCodes.NOT_FOUND, "fuck!", HttpStatus.NOT_FOUND);
        this.responseResource = HttpResponseResource.getObject(this.exception);
    }

    @Test
    public void OBJECT_CREATION_TEST() {
        assertThat(this.responseResource.getType(), is(ExceptionTypes.MODEL_RELATED));
    }

}