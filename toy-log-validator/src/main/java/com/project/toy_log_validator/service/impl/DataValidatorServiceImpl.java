package com.project.toy_log_validator.service.impl;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.project.toy_log_validator.enums.Error;
import com.project.toy_log_validator.enums.Validation;
import com.project.toy_log_validator.exceptions.GenericException;
import com.project.toy_log_validator.service.DataValidatorService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataValidatorServiceImpl implements DataValidatorService {

    @Value("${logs.validator.regex.percentage}")
    @Setter
    private String percentageRegex;

    @Override
    public Validation dataValidation(CSVReader reportSheet, String uuid) throws GenericException, CsvValidationException, IOException {
        log.info("X-Tracker: {} | validating data", uuid);
        log.debug("X-Tracker: {} | request id: {}", reportSheet);

        String[] nextLine = reportSheet.readNext(); // grab header
        String[] reportHeaders = Arrays.copyOf(nextLine, nextLine.length);

        nextLine = reportSheet.readNext(); // skipping to row 1, no need to process headers
        while (nextLine != null) {
            for(int i = 0; i < nextLine.length; i++) {
                String cellValue =  nextLine[i].trim();
                String header = reportHeaders[i].trim();

                if(cellValue == null || StringUtils.isBlank(cellValue) || StringUtils.isEmpty(cellValue)) {
                    throw new GenericException(Error.DATA_EMP_ERROR);
                } else if (header.equals("DIRECT_HIT") || header.equals("CRIT_HIT")) {
                    if(!cellValue.matches(percentageRegex)) {
                        throw new GenericException(Error.DATA_PRC_ERROR);
                    }

                    cellValue = cellValue.substring(0, cellValue.length() - 1);
                    if(!NumberUtils.isParsable(cellValue)) {
                        throw new GenericException(Error.DATA_DMG_ERROR);
                    } else if (Double.parseDouble(cellValue) / 100 > 1) {
                        throw new GenericException(Error.DATA_PRC_ERROR);
                    }
                } else if (header.equals("DAMAGE")) {
                    if(!NumberUtils.isParsable(cellValue)) {
                        throw new GenericException(Error.DATA_DMG_ERROR);
                    }
                }
            }

            nextLine = reportSheet.readNext();
        }

        return Validation.PASS;
    }

}
