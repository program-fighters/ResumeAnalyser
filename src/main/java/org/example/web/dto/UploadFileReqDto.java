package org.example.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Constant.DbConstant;

@Data
@NoArgsConstructor
public class UploadFileReqDto {
    @JsonProperty(DbConstant.CandidatesInfo.FILE_NAME)
    private String fileName;
    @JsonProperty(DbConstant.CandidatesInfo.NAME)
    private String candidateName;
    @JsonProperty(DbConstant.CandidatesInfo.EMAIL)
    private String emailId;
}
