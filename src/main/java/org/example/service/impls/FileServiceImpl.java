package org.example.service.impls;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.service.FileProcessor;
import org.example.service.FileService;
import org.example.web.dto.UploadAppResponseDto;
import org.example.web.dto.UploadFileReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service(value = "fileService")
public class FileServiceImpl extends BaseServiceImpl implements FileService {

    @Autowired
    private FileProcessor fileProcessor;

    @Override
    public UploadAppResponseDto uploadFile(
            MultipartFile file,
            UploadFileReqDto request,
            HttpServletRequest httpServletRequest) {
        log.debug("Inside uploadFile() :: file ::{},request::{}", file.getName(), request);
        fileProcessor.processFileAndSaveInDb(file, request);
        return UploadAppResponseDto.builder().message("We will process your resume soon.").build();
    }
}
