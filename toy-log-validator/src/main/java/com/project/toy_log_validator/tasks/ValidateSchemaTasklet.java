package com.project.toy_log_validator.tasks;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.project.toy_log_validator.enums.Validation;
import com.project.toy_log_validator.service.ReportService;
import com.project.toy_log_validator.service.SchemaValidatorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@JobScope
public class ValidateSchemaTasklet implements Tasklet {

    @Autowired
    private ReportService reportService;

    @Autowired
    private SchemaValidatorService validatorService;

    @Value("#{jobParameters['uuid']}")
    private String uuid;

    @Value("#{jobParameters['reportId']}")
    private String fileId;

    @Value("#{jobParameters['colSize']}")
    private double colSize;

    @Value("#{jobParameters['rowSize']}")
    private double rowSize;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("X-Tracker: {} | task one: schema validation", uuid);

        double reportSize[] = {colSize, rowSize};

        CSVReader csvReport = reportService.getReportCsv(fileId, uuid);
        Validation response = validatorService.schemaValidate(reportSize, csvReport, uuid);

        if(response.equals(Validation.PASS)) {
            return RepeatStatus.FINISHED;
        } else {
            // TODO: push a message to a kafka topic

            return RepeatStatus.FINISHED;
        }
    }
}
