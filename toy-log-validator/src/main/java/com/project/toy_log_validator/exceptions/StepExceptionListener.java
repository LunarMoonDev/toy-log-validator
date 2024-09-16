package com.project.toy_log_validator.exceptions;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.toy_log_validator.enums.Error;
import com.project.toy_log_validator.service.ReportService;

@Component
public class StepExceptionListener implements StepExecutionListener {
    @Autowired
    private ReportService service;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // grab job parameters
        JobParameters jobParameter = stepExecution.getJobExecution().getJobParameters();
        String uuid = jobParameter.getString("uuid");
        String reportId = jobParameter.getString("reportId");

        List<Throwable> exceptions = stepExecution.getFailureExceptions();
        exceptions.stream()
                .forEach(err -> {
                    if (err instanceof ValidationException) {
                        ValidationException error = (ValidationException) err;
                        service.saveEvent(uuid, reportId, error.getStatus(), error.getEvent());
                    } else {
                        service.saveEvent(uuid, reportId, Error.SYSTEM_ERROR.getCode(),
                                Error.SYSTEM_ERROR.getMessage());
                    }
                });

        boolean hasGeneric = exceptions.stream().anyMatch(err -> err instanceof ValidationException);
        if (hasGeneric) {
            return ExitStatus.FAILED;
        }

        return stepExecution.getExitStatus();
    }
}
