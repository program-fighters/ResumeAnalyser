package org.example.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.example.exception.AppException;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.example.exception.BaseErrorEnum.INVALID_REQ_PAYLOAD_MESSAGE;
import static org.example.exception.BaseErrorEnum.REQUEST_READ_FAILURE_CODE;

public class BaseManager {
    @Autowired
    protected FileService fileService;
    @Autowired
    protected ObjectMapper objectMapper;

    protected <T> T parseAnyDtoClassFromJsonString(String jsonString, Class<T> tClass) {
        if (ObjectUtils.isEmpty(jsonString) || ObjectUtils.isEmpty(tClass)) {
            throw new AppException(INVALID_REQ_PAYLOAD_MESSAGE);
        }
        try {
            return objectMapper.readValue(jsonString, tClass);
        } catch (JsonProcessingException e) {
            throw new AppException(REQUEST_READ_FAILURE_CODE);
        }
    }
}
