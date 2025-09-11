package org.example.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Constant.DbConstant;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = DbConstant.Collection.CandidatesInfo)
public class CandidatesInfo extends AuditTrails {

    @JsonProperty(DbConstant.CandidatesInfo.NAME)
    @Field(DbConstant.CandidatesInfo.NAME)
    private String name;

    @UniqueElements(message = "mailId should be unique.")
    @JsonProperty(DbConstant.CandidatesInfo.EMAIL)
    @Field(DbConstant.CandidatesInfo.EMAIL)
    private String email;

    @JsonProperty(DbConstant.CandidatesInfo.SKILLS)
    @Field(DbConstant.CandidatesInfo.SKILLS)
    private String skills;

    @JsonProperty(DbConstant.CandidatesInfo.EXPERIENCE)
    @Field(DbConstant.CandidatesInfo.EXPERIENCE)
    private String experience;

    @JsonProperty(DbConstant.CandidatesInfo.SUMMARY)
    @Field(DbConstant.CandidatesInfo.SUMMARY)
    private Date summary;

    @JsonProperty(DbConstant.CandidatesInfo.RESUME_TEXT)
    @Field(DbConstant.CandidatesInfo.RESUME_TEXT)
    private String resumeText;

    @JsonIgnore
    @Field(DbConstant.CandidatesInfo.UPDATE_COUNT)
    private int updateCount = 0;
}
