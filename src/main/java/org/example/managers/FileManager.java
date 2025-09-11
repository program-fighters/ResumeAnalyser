package org.example.managers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.example.Constant.RegexConstant;
import org.example.exception.AppException;
import org.example.exception.BaseErrorEnum;
import org.example.utils.FileUtils;
import org.example.utils.NullSafeUtils;
import org.example.web.dto.UploadAppResponseDto;
import org.example.web.dto.UploadFileReqDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileManager extends BaseManager {
    public UploadAppResponseDto uploadFile(
            MultipartFile file,
            String fileInfo,
            HttpServletRequest httpServletRequest) {
        log.debug("Inside uploadFile() manager.");
        if (file == null || file.isEmpty()) {
            throw new AppException(BaseErrorEnum.EMPTY_MULTIPART);
        }
        if (ObjectUtils.isEmpty(fileInfo)) {
            throw new AppException(BaseErrorEnum.INVALID_REQ_PAYLOAD_MESSAGE);
        }
        //parse request in string
        UploadFileReqDto uploadFileReqDto = parseAnyDtoClassFromJsonString(fileInfo, UploadFileReqDto.class);
        if (ObjectUtils.isEmpty(uploadFileReqDto)) {
            throw new AppException(BaseErrorEnum.INVALID_REQ_PAYLOAD_MESSAGE);
        }
        if (NullSafeUtils.isEmpty(uploadFileReqDto.getEmailId())) {
            throw new AppException(BaseErrorEnum.MISSING_MAIL_ID_ERROR);
        }
        if (!uploadFileReqDto.getEmailId().matches(RegexConstant.VALID_EMAIL_REGX)) {
            throw new AppException(BaseErrorEnum.INVALID_MAIL_ID_ERROR);
        }
        if (!FileUtils.isFileValidPdfOrDoc(file)) {
            throw new AppException(BaseErrorEnum.INVALID_RESUME_FILE_EXT_ERROR);
        }
        //call service
        return fileService.uploadFile(file, uploadFileReqDto, httpServletRequest);
    }
}
