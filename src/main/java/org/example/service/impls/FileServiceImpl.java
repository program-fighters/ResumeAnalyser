package org.example.service.impls;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.service.FileService;
import org.example.web.dto.UploadAppResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class FileServiceImpl extends BaseServiceImpl implements FileService {

    @Override
    public UploadAppResponseDto uploadFile(
            MultipartFile file,
            String resumeInfo,
            HttpServletRequest httpServletRequest) {
        log.debug("Inside uploadFile() :: file ::{},resumeInfo::{}", file.getName(), resumeInfo);
        return UploadAppResponseDto.builder().message("We will process your resume soon.").build();
    }
}
