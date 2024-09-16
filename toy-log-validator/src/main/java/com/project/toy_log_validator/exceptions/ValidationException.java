package com.project.toy_log_validator.exceptions;

import com.project.toy_log_validator.enums.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    private String event;
    private String status;
    private String reportId;
    private String uuid;

    public ValidationException(Error error, String reportId, String uuid){
        super(error.getMessage());
        
        this.event = error.getMessage();
        this.reportId = reportId;
        this.uuid = uuid;
        this.status = error.getCode();
    }
}
