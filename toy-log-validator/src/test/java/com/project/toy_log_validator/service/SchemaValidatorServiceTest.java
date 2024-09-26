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
import com.project.toy_log_validator.service.impl.SchemaValidatorServiceImpl;

public class SchemaValidatorServiceTest {
    
    @InjectMocks
    private SchemaValidatorServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public CSVReader createCsvReader(String mockCsvReport) {
        return new CSVReader(new StringReader(mockCsvReport));
    }

    public String createPassReport() {
        return "FIRST_NAME,LAST_NAME,SERVER,JOB,DIRECT_HIT,CRIT_HIT,DAMAGE\n" +
                "Igor,Ozouf,Kujata,VPR,89%,13%,2345\n" +
                "Adrianne,Dufresne,Kujata,SAM,56%,26%,7452\n";
    }

    public String createFailReport_HeaderSize() {
        return "FIRST_NAME,LAST_NAME,SERVER,DIRECT_HIT,CRIT_HIT,DAMAGE\n" +
                "Igor,Ozouf,Kujata,VPR,89%,13%,2345\n" +
                "Adrianne,Dufresne,Kujata,SAM,56%,26%,7452\n";
    }

    public String createFailReport_Header() {
        return "FIRST_NAME,LAST_NAME,WORLD,JOB,DIRECT_HIT,CRIT_HIT,DAMAGE\n" +
                "Igor,Ozouf,Kujata,VPR,89%,13%,2345\n" +
                "Adrianne,Dufresne,Kujata,SAM,56%,26%,7452\n";
    }

    public String createFailReport_RowSize() {
        return "FIRST_NAME,LAST_NAME,SERVER,JOB,DIRECT_HIT,CRIT_HIT,DAMAGE\n" +
                "Igor,Ozouf,Kujata,VPR,2345\n" +
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
        double []size = {7, 2};

        Validation validation = service.schemaValidate(size, reader, uuid);

        assertEquals(Validation.PASS, validation);
    }

    @Test
    public void testDataValidation_HeaderSize() throws CsvValidationException, GenericException, IOException {
        String dummyReport = createFailReport_HeaderSize();
        CSVReader reader = createCsvReader(dummyReport);
        String uuid = createUuid();
        double []size = {7, 2};

        GenericException exception = assertThrows(GenericException.class, () -> {
            Validation validation = service.schemaValidate(size, reader, uuid);
        });

        assertEquals(Error.SCHEMA_COL_ERROR, exception.getError());
    }

    @Test
    public void testDataValidation_Header() throws CsvValidationException, GenericException, IOException {
        String dummyReport = createFailReport_Header();
        CSVReader reader = createCsvReader(dummyReport);
        String uuid = createUuid();
        double []size = {7, 2};

        GenericException exception = assertThrows(GenericException.class, () -> {
            Validation validation = service.schemaValidate(size, reader, uuid);
        });

        assertEquals(Error.SCHEMA_COL_ERROR, exception.getError());
    }

    @Test
    public void testDataValidation_RowSize() throws CsvValidationException, GenericException, IOException {
        String dummyReport = createFailReport_RowSize();
        CSVReader reader = createCsvReader(dummyReport);
        String uuid = createUuid();
        double []size = {7, 2};

        GenericException exception = assertThrows(GenericException.class, () -> {
            Validation validation = service.schemaValidate(size, reader, uuid);
        });

        assertEquals(Error.SCHEMA_COL_ERROR, exception.getError());
    }
}
