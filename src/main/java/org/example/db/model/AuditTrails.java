package org.example.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.example.utils.DateUtils;
import org.example.utils.NullSafeUtils;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

import static org.example.Constant.DbConstant.AuditTrails.*;


@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = {INS_TS, INS_BY, UPD_TS, UPD_BY})
public class AuditTrails extends Auditable {

    @Field(name = INS_TS)
    @JsonIgnore
    private Date insertTimeStamp;

    @Field(name = INS_BY)
    @JsonProperty(value = INS_BY)
    private String insertedBy;

    @Field(name = UPD_TS)
    @JsonIgnore
    private Date updateTimeStamp;

    @Field(name = UPD_BY)
    @JsonProperty(value = UPD_BY)
    private String UpdatedBy;

    @JsonProperty(value = INS_TS)
    public String getInsertTimeStamp() {
        return NullSafeUtils.isAmNullOrEmpty(insertTimeStamp) ? null : DateUtils.dateInDmsFormatForReport(insertTimeStamp);
    }

    @JsonProperty(value = UPD_TS)
    public String getUpdateTimeStamp() {
        return NullSafeUtils.isAmNullOrEmpty(updateTimeStamp) ? null : DateUtils.dateInDmsFormatForReport(updateTimeStamp);
    }

}
