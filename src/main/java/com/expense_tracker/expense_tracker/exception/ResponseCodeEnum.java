package com.expense_tracker.expense_tracker.exception;

public enum ResponseCodeEnum {

    EMAIL_ALREADY_EXISTS("U001","EmailId already exists"),
    ROLE_NOT_FOUND("R001","Role not found" ),
    USER_NOT_FOUND("U002","User not found" ),
    OTP_EXPIRED_OR_NOT_FOUND("P001","otp expired or not found"),
    INVALID_TIME_RANGE("E001","Invalid time range" ),
    EXPENSE_NOT_FOUND("E002","expense not found" ),
    USERROLE_NOT_FOUND("UR001","UserRole not found" );


    private final String statusCode;
    private final String message;

    ResponseCodeEnum(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
