package com.project.toy_log_validator.service;

import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.project.toy_log_validator.enums.Validation;
import com.project.toy_log_validator.exceptions.GenericException;

public interface DataValidatorService {
    String[] schema = {"STRING", "STRING", "STRING", "STRING", "PER", "PER", "INT"};

    Validation dataValidation(CSVReader reportSheet, String uuid) throws GenericException, CsvValidationException, IOException;
}
