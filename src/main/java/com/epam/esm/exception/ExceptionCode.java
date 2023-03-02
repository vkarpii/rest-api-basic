package com.epam.esm.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    TAG_IS_ALREADY_EXISTS("409-01", ExceptionMessage.TAG_IS_ALREADY_EXISTS),
    TAG_NOT_FOUND("404-01", ExceptionMessage.TAG_NOT_FOUND),
    CERTIFICATE_NOT_FOUND("404-02", ExceptionMessage.CERTIFICATE_NOT_FOUND),
    CERTIFICATE_IS_ALREADY_EXISTS("409-02", ExceptionMessage.CERTIFICATE_IS_ALREADY_EXISTS),
    BAD_ATTRIBUTES("400-01", ExceptionMessage.BAD_ATTRIBUTES);
    private final String code;
    private final String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCodeByMessage(String message) {
        String code = null;
        ExceptionCode[] enums = ExceptionCode.values();
        for (ExceptionCode element : enums) {
            if (element.getMessage().equals(message)) {
                code = element.getCode();
            }
        }
        return code;
    }

}
