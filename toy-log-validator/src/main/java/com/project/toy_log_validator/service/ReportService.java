package com.project.toy_log_validator.service;

import java.io.IOException;

import com.opencsv.CSVReader;
import com.project.toy_log_validator.entity.Reports;

public interface ReportService {
    Reports getLatestReport(String uuid);
    CSVReader getReportCsv(String fileId, String uuid) throws IOException;
}
