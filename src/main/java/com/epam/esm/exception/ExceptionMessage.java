package com.epam.esm.exception;

import lombok.extern.java.Log;

@Log
public final class ExceptionMessage {
    public final static String TAG_IS_ALREADY_EXISTS = "error.message.tag.is.already.exists";
    public final static String TAG_NOT_FOUND = "error.message.tag.not.found";

    public static final String CERTIFICATE_NOT_FOUND = "error.message.certificate.not.found";
    public static final String CERTIFICATE_IS_ALREADY_EXISTS = "error.message.certificate.is.already.exists";
    public static final String BAD_ATTRIBUTES = "error.message.bad.attributes";

    private ExceptionMessage() {
    }
}
