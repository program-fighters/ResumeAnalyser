package org.example.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = {"serviceName", "statusCode", "message", "data"})
public class Response<T> implements Serializable {
    private T data;
    private String statusCode;
    private String message;
    @JsonIgnore
    private int httpsCode;
    private String serviceName;

    public static <T> Response<T> ok(T data, String serviceName) {
        return ok(data, serviceName, "Request processed Successfully.");
    }

    public static <T> Response<T> deleted(T data, String serviceName) {
        return ok(data, serviceName, "Resource deleted successfully.");
    }

    public static <T> Response<T> ok(T data, String serviceName, String msg) {
        return createResponse(data, serviceName, msg, String.valueOf(HttpStatus.OK.value()));
    }

    public static <T> Response<T> created(T data, String serviceName) {
        return created(data, serviceName, "Resource created successfully.");
    }


    public static <T> Response<T> requestAccepted(T data, String serviceName) {
        return requestAccepted(data, serviceName, "Request Accepted.");
    }

    public static <T> Response<T> requestAccepted(T data, String serviceName, String msg) {
        return createResponse(data, serviceName, msg, String.valueOf(HttpStatus.ACCEPTED.value()));
    }

    public static <T> Response<T> created(T data, String serviceName, String msg) {
        return createResponse(data, serviceName, msg, "201");
    }

    public static Response<String> badRequest(String serviceName, String msg) {
        return badRequest(serviceName, "400", msg);
    }

    public static Response<String> badRequest(String serviceName, String errorCode, String errorMsg) {
        return createResponse("", serviceName, errorMsg, errorCode);
    }

    public static Response<String> failedRequest(String serviceName) {
        return createResponse("", serviceName, "500", "Error occurred while process request.");
    }


    public static <T> Response<T> createResponse(T data, String serviceName, String msg, String statusCode) {
        return Response
                .<T>builder()
                .message(msg)
                .data(data)
                .statusCode(statusCode)
                .serviceName(serviceName)
                .build();
    }
}
