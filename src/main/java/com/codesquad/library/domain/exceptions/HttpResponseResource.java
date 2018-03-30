package com.codesquad.library.domain.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class HttpResponseResource {

    private ExceptionTypes type;
    private HttpStatus status;
    private ErrorCodes code;
    private String message;

    public static HttpResponseResource getObject(ApplicationException exception) {

        return HttpResponseResource.builder()
                .type(ExceptionTypes.findByInstance(exception))
                .status(exception.getStatus())
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
    }
}
