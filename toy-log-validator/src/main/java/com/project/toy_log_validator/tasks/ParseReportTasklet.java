package com.project.toy_log_validator.tasks;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.toy_log_validator.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@JobScope
public class ParseReportTasklet implements Tasklet {
    @Autowired
    private ReportService reportService;

    @Value("#{jobParameters['uuid']}")
    private String uuid;
    
    @Value("#{jobParameters['reportId']}")
    private String reportId;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("X-Tracker: {} | task one: transition to done", uuid);

        reportService.processReport(uuid, reportId);

        return RepeatStatus.FINISHED;
    }
}
