package com.project.toy_log_validator.tasks;

import java.io.IOException;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.project.toy_log_validator.enums.Validation;
import com.project.toy_log_validator.exceptions.GenericException;
import com.project.toy_log_validator.exceptions.ValidationException;
import com.project.toy_log_validator.service.DataValidatorService;
import com.project.toy_log_validator.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@JobScope
public class ValidateDataTasklet implements Tasklet {

    @Autowired
    private ReportService reportService;

    @Autowired
    private DataValidatorService validatorService;

    @Value("#{jobParameters['uuid']}")
    private String uuid;

    @Value("#{jobParameters['reportId']}")
    private String fileId;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws ValidationException, CsvValidationException, IOException {
        log.info("X-Tracker: {} | task one: data validation", uuid);

        try {
            CSVReader csvReport = reportService.getReportCsv(fileId, uuid);
            Validation response = validatorService.dataValidation(csvReport, uuid);

            return RepeatStatus.FINISHED;
        } catch (GenericException exception) {
            throw new ValidationException(exception.getError(), fileId, uuid);
        }
    }
}
