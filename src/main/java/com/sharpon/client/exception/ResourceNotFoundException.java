package com.sharpon.client.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;

public class ResourceNotFoundException extends ErrorResponseException {
    public ResourceNotFoundException(HttpStatusCode status) {
        super(status);
    }
}
