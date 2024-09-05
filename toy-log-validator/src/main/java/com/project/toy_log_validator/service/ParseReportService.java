package com.project.toy_log_validator.service;

import com.opencsv.CSVReader;

import java.io.IOException;

public interface ParseReportService {
    CSVReader getReport(String fileId, String uuid) throws IOException;
}
