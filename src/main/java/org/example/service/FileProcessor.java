package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.db.model.CandidatesInfo;
import org.example.service.impls.BaseServiceImpl;
import org.example.utils.DateUtils;
import org.example.utils.FileUtils;
import org.example.utils.NullSafeUtils;
import org.example.utils.StringUtils;
import org.example.web.dto.UploadFileReqDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class FileProcessor extends BaseServiceImpl {
    @Async
    public void processFileAndSaveInDb(MultipartFile file, UploadFileReqDto reqDto) {
        log.debug("Inside processFileAndSaveInDb() with fileName::{} and candidate::{}", file.getOriginalFilename(), reqDto.getCandidateName());
        //create resume object and save data.
        String resumeFileText = FileUtils.readTextFromPdfOrDocMultipartRequest(file);
        if (StringUtils.isEmpty(resumeFileText)) {
            log.debug("resume File text is empty for candidate::{} ", reqDto.getCandidateName());
            return;
        }
        CandidatesInfo toBeSaveInDb = candidateInfoRepository
                .findByEmail(reqDto.getEmailId())
                .orElse(new CandidatesInfo());
        toBeSaveInDb.setEmail(reqDto.getEmailId());
        toBeSaveInDb.setName(reqDto.getCandidateName());
        toBeSaveInDb.setResumeText(resumeFileText);
        boolean newEntity = NullSafeUtils.isAmNullOrEmpty(toBeSaveInDb.getId());
        if (newEntity) {
            toBeSaveInDb.setInsertedBy("admin");
            toBeSaveInDb.setInsertTimeStamp(DateUtils.currentDate());
            toBeSaveInDb.setUpdateCount(1);
        } else {
            toBeSaveInDb.setUpdatedBy("admin");
            toBeSaveInDb.setUpdateTimeStamp(DateUtils.currentDate());
            toBeSaveInDb.setUpdateCount(toBeSaveInDb.getUpdateCount() + 1);
        }
        candidateInfoRepository.save(toBeSaveInDb);
        log.debug("CandidatesInfo is saved on db for candidate::{}", reqDto.getCandidateName());
    }
}
