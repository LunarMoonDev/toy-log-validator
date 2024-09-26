package com.project.toy_log_validator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.project.toy_log_validator.enums.Error;
import com.project.toy_log_validator.enums.Validation;
import com.project.toy_log_validator.exceptions.GenericException;
import com.project.toy_log_validator.service.impl.DataValidatorServiceImpl;

public class DataValidatorServiceTest {

    @InjectMocks
    private DataValidatorServiceImpl service;

    // vars
    private String percentageRegex = "\\d+(?:\\.\\d+)?%";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service.setPercentageRegex(this.percentageRegex);
    }

    public CSVReader createCsvReader(String mockCsvReport) {
        return new CSVReader(new StringReader(mockCsvReport));
    }

    public String createPassReport() {
        return "FIRST_NAME,LAST_NAME,SERVER,JOB,DIRECT_HIT,CRIT_HIT,DAMAGE\n" +
                "Igor,Ozouf,Kujata,VPR,89%,13%,2345\n" +
                "Adrianne,Dufresne,Kujata,SAM,56%,26%,7452\n";
    }

    public String createFailReport_DHPercent() {
        return "FIRST_NAME,LAST_NAME,SERVER,JOB,DIRECT_HIT,CRIT_HIT,DAMAGE\n" +
                "Igor,Ozouf,Kujata,VPR,89,13%,2345\n" +
                "Adrianne,Dufresne,Kujata,SAM,56%,26%,7452\n";
    }

    public String createFailReport_DH100() {
        return "FIRST_NAME,LAST_NAME,SERVER,JOB,DIRECT_HIT,CRIT_HIT,DAMAGE\n" +
                "Igor,Ozouf,Kujata,VPR,892%,13%,2345\n" +
                "Adrianne,Dufresne,Kujata,SAM,56%,26%,7452\n";
    }

    public String createFailReport_CH100() {
        return "FIRST_NAME,LAST_NAME,SERVER,JOB,DIRECT_HIT,CRIT_HIT,DAMAGE\n" +
                "Igor,Ozouf,Kujata,VPR,89%,1311%,2345\n" +
                "Adrianne,Dufresne,Kujata,SAM,56%,26%,7452\n";
    }

    public String createFailReport_CHPercent() {
        return "FIRST_NAME,LAST_NAME,SERVER,JOB,DIRECT_HIT,CRIT_HIT,DAMAGE\n" +
                "Igor,Ozouf,Kujata,VPR,89%,13,2345\n" +
                "Adrianne,Dufresne,Kujata,SAM,56%,26%,7452\n";
    }

    public String createUuid() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void testDataValidation() throws CsvValidationException, GenericException, IOException {
        String dummyReport = createPassReport();
        CSVReader reader = createCsvReader(dummyReport);
        String uuid = createUuid();

        Validation validation = service.dataValidation(reader, uuid);

        assertEquals(Validation.PASS, validation);
    }

    public void testDataValidation_FailDHPercent() throws CsvValidationException, GenericException, IOException {
        String dummyReport = createFailReport_DHPercent();
        CSVReader reader = createCsvReader(dummyReport);
        String uuid = createUuid();

        GenericException exception = assertThrows(GenericException.class, () -> {
            Validation validation = service.dataValidation(reader, uuid);
        });

        assertEquals(Error.DATA_PRC_ERROR, exception.getError());
    }

    @Test
    public void testDataValidation_FailCHPercent() throws CsvValidationException, GenericException, IOException {
        String dummyReport = createFailReport_CHPercent();
        CSVReader reader = createCsvReader(dummyReport);
        String uuid = createUuid();

        GenericException exception = assertThrows(GenericException.class, () -> {
            Validation validation = service.dataValidation(reader, uuid);
        });

        assertEquals(Error.DATA_PRC_ERROR, exception.getError());
    }

    @Test
    public void testDataValidation_FailCH100() throws CsvValidationException, GenericException, IOException {
        String dummyReport = createFailReport_CH100();
        CSVReader reader = createCsvReader(dummyReport);
        String uuid = createUuid();

        GenericException exception = assertThrows(GenericException.class, () -> {
            Validation validation = service.dataValidation(reader, uuid);
        });

        assertEquals(Error.DATA_PRC_ERROR, exception.getError());
    }

    @Test
    public void testDataValidation_FailDH100() throws CsvValidationException, GenericException, IOException {
        String dummyReport = createFailReport_DH100();
        CSVReader reader = createCsvReader(dummyReport);
        String uuid = createUuid();

        GenericException exception = assertThrows(GenericException.class, () -> {
            Validation validation = service.dataValidation(reader, uuid);
        });

        assertEquals(Error.DATA_PRC_ERROR, exception.getError());
    }
}
