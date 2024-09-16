package com.project.toy_log_validator.service.impl;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.project.toy_log_validator.enums.Error;
import com.project.toy_log_validator.enums.Validation;
import com.project.toy_log_validator.exceptions.GenericException;
import com.project.toy_log_validator.service.SchemaValidatorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchemaValidatorServiceImpl implements SchemaValidatorService {

    @Override
    public Validation schemaValidate(double []reportSize, CSVReader reportSheet, String uuid) throws GenericException, CsvValidationException, IOException {
        log.info("X-Tracker: {} | validating schema", uuid);
        log.debug("X-Tracker: {} | request id: {}", uuid, reportSheet);

        String[] nextLine = reportSheet.readNext();
        String[] currHeaders = Arrays.copyOf(nextLine, nextLine.length);

        while(nextLine != null) {
            if(nextLine.length != headers.size()) {
                throw new GenericException(Error.SCHEMA_COL_ERROR); 
            }
            nextLine = reportSheet.readNext();
        }

        for(String currHeader: currHeaders) {
            
            String trimmedHeader = currHeader.trim();
            if(!headers.contains(trimmedHeader)) {
                throw new GenericException(Error.SCHEMA_COL_ERROR);
            }
        }
        
        return Validation.PASS;
    }
}
