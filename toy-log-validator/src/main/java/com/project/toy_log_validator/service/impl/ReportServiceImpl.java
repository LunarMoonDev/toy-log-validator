package com.project.toy_log_validator.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.project.toy_log_validator.entity.Reports;
import com.project.toy_log_validator.enums.Validation;
import com.project.toy_log_validator.exceptions.GenericException;
import com.project.toy_log_validator.repository.ReportsRepository;
import com.project.toy_log_validator.service.ParseReportService;
import com.project.toy_log_validator.service.ReportService;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportsRepository repository;

    @Autowired
    ParseReportService parseReportService;

    @Override
    public CSVReader getReportCsv(String fileId, String uuid) throws IOException {
        log.info("X-Tracker: {} | retrieving report as csv", uuid);
        log.debug("X-Tracker: {} | request id: {}", fileId);

        return parseReportService.getReport(fileId, uuid);
    }

    @Override
    public Reports getLatestReport(String uuid) {
        log.info("X-Tracker: {} | retrieving last report", uuid);

        return repository.findFirstByOrderByGmtCreateDesc();
    }

    @Override
    public void processReport(String uuid, String reportId) throws GenericException {
        log.info("X-Tracker: {} | updating report to processed", uuid);
        Optional<Reports> response = repository.findByReportId(reportId);

        response.ifPresent((report) -> {
            report.setIsProcessed(true);
            report.setIsError(false);
            report.setEvent(Validation.PASS.getMessage());
            report.setStatus(Validation.PASS.getCode());
            repository.save(report);
        });
    }

    @Override
    public void saveEvent(String uuid, String reportId, String status, String event) {
        log.info("X-Tracker: {} | updating report for error event", uuid);
        Optional<Reports> response = repository.findByReportId(reportId);

        response.ifPresent((report) -> {
            report.setEvent(event);
            report.setStatus(status);
            report.setIsProcessed(true);
            report.setIsError(true);
            repository.save(report);
        });
    }
}
