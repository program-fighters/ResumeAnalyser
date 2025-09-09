package org.example.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.Constant.UrlConstants;
import org.example.web.dto.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(UrlConstants.FILE)
public class FileController extends BaseController {


    @PostMapping(value = UrlConstants.Upload, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> uploadFile(
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestParam(name = "info", required = false) String resumeInfo,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        Response<?> response = null;
        final String serviceName = getBaseUri() + UrlConstants.Upload;
        log.debug("Inside uploadFile():: with service::{}", serviceName);
        try {
            response = Response.requestAccepted(fileManager.uploadFile(file, resumeInfo, httpServletRequest), serviceName);
        } catch (Throwable throwable) {
            response = handleExceptionSendReadableResponse(serviceName, throwable);
        }
        response = updateResponseStatusCode(response, httpServletResponse);
        return response;
    }

    @Override
    protected String getBaseUri() {
        return UrlConstants.FILE;
    }
}
