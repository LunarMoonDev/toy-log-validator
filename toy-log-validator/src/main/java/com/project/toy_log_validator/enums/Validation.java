package com.project.toy_log_validator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Validation {
    PASS("VAL-001", "Report file passed the validation pipeline"),
    FAIL("VAL-002", "Report file failed the validation pipeline");

    private final String code;
    private final String message;
}
