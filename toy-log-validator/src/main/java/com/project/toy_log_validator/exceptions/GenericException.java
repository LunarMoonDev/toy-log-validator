package com.project.toy_log_validator.exceptions;

import com.project.toy_log_validator.enums.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericException extends RuntimeException{
    private Error error;
}
