package com.project.toy_log_validator.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Arrays;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.project.toy_log_validator.enums.Validation;
import com.project.toy_log_validator.exceptions.GenericException;

public interface SchemaValidatorService {
    HashSet<String> headers = new HashSet<>(Arrays.asList("FIRST_NAME", "LAST_NAME", "SERVER", "JOB", "DIRECT_HIT", "CRIT_HIT", "DAMAGE"));

    Validation schemaValidate(double []reportSize, CSVReader reportSheet, String uuid)  throws GenericException, CsvValidationException, IOException;
}
