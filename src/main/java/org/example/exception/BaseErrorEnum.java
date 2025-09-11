package org.example.exception;

import org.springframework.http.HttpStatus;

public enum BaseErrorEnum {
    /* 200 */
    SUCCESS(HttpStatus.OK, "", "Request processed Successfully."),

    /* 201 */
    CREATED(HttpStatus.CREATED, "", "Resource created successfully."),

    /* 400 */
    FILE_NOT_UPLOADED(HttpStatus.BAD_REQUEST, "", "CSV File not uploaded."),
    EMPTY_MULTIPART(HttpStatus.BAD_REQUEST, "", "Multipart request is not valid."),
    REQUEST_READ_FAILURE_CODE(HttpStatus.BAD_REQUEST, "", "Error occurred while reading your json request body."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "", "BAD REQUEST."),
    INVALID_MAIL_ID_ERROR(HttpStatus.BAD_REQUEST, "", "Invalid candidate mailId."),
    MISSING_MAIL_ID_ERROR(HttpStatus.BAD_REQUEST, "", "Please enter candidate mailId."),
    INVALID_REQ_PAYLOAD_MESSAGE(HttpStatus.BAD_REQUEST, "", "Invalid request payload."),
    INVALID_RESUME_INFO_ERROR(HttpStatus.BAD_REQUEST, "", "Invalid resume info."),
    INVALID_RESUME_FILE_EXT_ERROR(HttpStatus.BAD_REQUEST, "", "we support only pdf or doc file."),
    DATE_CONVERT_EXCEPTION(HttpStatus.BAD_REQUEST, "", "Date Convert Failed."),
    BAD_DATE_FORMAT(HttpStatus.BAD_REQUEST, "", "Bad Date Format."),
    DATE_PARSE_EXCEPTION(HttpStatus.BAD_REQUEST, "", "Date parsed Failed."),

    /* 500 */
    FILE_PROCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "", "File process error");

    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

    BaseErrorEnum(HttpStatus httpStatus, String customCode, String message) {
        this.httpStatus = httpStatus;
        this.customCode = customCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return org.apache.commons.lang3.StringUtils.isBlank(customCode) ? "AE" + httpStatus.value() : customCode;
    }
}
