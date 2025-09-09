package org.example.managers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.AppException;
import org.example.exception.BaseErrorEnum;
import org.example.web.dto.UploadAppResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileManager extends BaseManager {
    public UploadAppResponseDto uploadFile(
            MultipartFile file,
            String resumeInfo,
            HttpServletRequest httpServletRequest) {
        log.debug("Inside uploadFile() manager.");
        if (file == null || file.isEmpty()) {
            throw new AppException(BaseErrorEnum.EMPTY_MULTIPART);
        }
        if (resumeInfo == null || resumeInfo.isEmpty()) {
            throw new AppException(BaseErrorEnum.EMPTY_MULTIPART);
        }
        return fileService.uploadFile(file, resumeInfo, httpServletRequest);
    }
}
