package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.web.dto.Response;
import org.example.web.dto.UploadAppResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService extends BaseService {
    UploadAppResponseDto uploadFile(
            MultipartFile file,
            String resumeInfo,
            HttpServletRequest httpServletRequest);
}
