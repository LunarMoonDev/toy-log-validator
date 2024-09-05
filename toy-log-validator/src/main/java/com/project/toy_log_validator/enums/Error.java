package com.project.toy_log_validator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Error {
    SCHEMA_COL_ERROR("SCH-COL-01", "Given report file does not follow the header format"),
    DATA_PRC_ERROR("DATA-PERC-01", "Given value must follow percentage formula"),
    DATA_DMG_ERROR("DATA-PERC-02", "Given value must be in numeric"),
    DATA_VAL_ERROR("DATA-PERC-03", "Given value must be in string format"),
    DATA_EMP_ERROR("DATA-EMP-04", "Given value must not be null or blank");

    private final String code;
    private final String message;
}
