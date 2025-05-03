package com.expense_tracker.expense_tracker.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final ResponseCodeEnum responseCodeEnum;
    public ResourceNotFoundException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.responseCodeEnum = responseCodeEnum;
    }
}
