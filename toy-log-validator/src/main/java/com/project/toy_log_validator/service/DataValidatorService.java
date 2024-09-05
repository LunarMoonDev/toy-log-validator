package com.project.toy_log_validator.service;

import com.opencsv.CSVReader;
import com.project.toy_log_validator.enums.Validation;

public interface DataValidatorService {
    String[] schema = {"STRING", "STRING", "STRING", "STRING", "PER", "PER", "INT"};

    Validation dataValidation(double []reportSize, CSVReader reportSheet, String uuid);
}
