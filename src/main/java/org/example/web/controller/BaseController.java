package org.example.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.exception.AppException;
import org.example.managers.FileManager;
import org.example.web.dto.Response;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public abstract class BaseController {
    @Autowired
    protected FileManager fileManager;


    protected Response<?> handleExceptionSendReadableResponse(
            String serviceName, Throwable exception) {
        if (exception == null) {
            return Response.ok("", serviceName);
        } else if (exception instanceof AppException appException) {
            log.warn("MdmAppException occurred while processing {} request.", serviceName);
            log.error(appException.getMessage(), appException);
            return Response.badRequest(
                    serviceName,
                    appException.getErrorCode(),
                    appException.getErrorMessage()
            );
        } else if (exception instanceof NoSuchBeanDefinitionException) {
            log.error("No such bean found exception thrown with cause :{}", exception.getMessage());
            return Response.failedRequest(serviceName);
        } else if (exception instanceof org.springframework.dao.DuplicateKeyException) {
            log.error("DuplicateKeyException occurred. in service :: " + serviceName, exception);
            return Response.badRequest(serviceName, "Duplicate constraint failed, Please take care of input[s].");
        } else {
            log.warn("Exception occurred while processing {} request.", serviceName);
            log.error(exception.getMessage(), exception);
            return Response.failedRequest(serviceName);
        }
    }

//    protected StreamingResponseBody handleExceptionAndCopyInHttpResponseDirectly(HttpServletResponse httpServletResponse, String serviceName, Throwable e) {
//        String json = "";
//        try {
//            long startTs = System.currentTimeMillis();
//            Response<?> errorResponse = handleExceptionSendReadableResponse(serviceName, e);
//            log.debug("Error response is::{}", errorResponse);
//            errorResponse = updateResponseStatusCode(errorResponse, httpServletResponse);
//            httpServletResponse.setContentType(JSON_CONTENT_TYPE);
//            json = new JSONObject(errorResponse).toString();
//            long endTs = System.currentTimeMillis();
//            log.debug("Total time taken to parse error is = {} ms.", endTs - startTs);
//        } catch (Exception err) {
//            log.error("Error occurred while writing error in response.", err);
//        }
//        String finalJson = json;
//        return outputStream -> {
//            try (PrintWriter writer = new PrintWriter(outputStream)) {
//                log.debug("Going to write  exception in stream response.");
//                writer.print(finalJson);
//                log.debug("Writing is completed in stream response.");
//            } catch (Exception err) {
//                log.error("Error occurred while writing response in stream response body.", err);
//            }
//        };
//    }

    protected <T> Response<T> updateResponseStatusCode(
            Response<T> response,
            HttpServletResponse httpServletResponse) {
        if (response == null) return null;
        //if application allow then it will convert
        if (response.getStatusCode() != null) {
            String status = response.getStatusCode();
            log.debug("Requested service is ::{},responded status is ::{}", response.getServiceName(), status);
            //remove prefix from code
            if (status.contains("AE")) {
                status = status.replace("AE", "");
            }
            //parse numeric value
            if (StringUtils.isNumeric(status)) {
                httpServletResponse.setStatus(Integer.parseInt(status));
            }
            return response;
        }
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return response;
    }

    protected abstract String getBaseUri();
}
