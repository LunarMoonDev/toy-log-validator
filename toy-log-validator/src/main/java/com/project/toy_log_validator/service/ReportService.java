package com.project.toy_log_validator.service;

import java.io.IOException;

import com.opencsv.CSVReader;
import com.project.toy_log_validator.entity.Reports;
import com.project.toy_log_validator.exceptions.GenericException;

public interface ReportService {
    Reports getLatestReport(String uuid);
    void processReport(String uuid, String reportId) throws GenericException;
    CSVReader getReportCsv(String fileId, String uuid) throws IOException;
}
