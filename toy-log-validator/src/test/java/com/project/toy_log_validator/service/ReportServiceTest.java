package com.project.toy_log_validator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.StringReader;

import com.opencsv.CSVReader;
import com.project.toy_log_validator.entity.Reports;
import com.project.toy_log_validator.repository.ReportsRepository;
import com.project.toy_log_validator.service.impl.ReportServiceImpl;

public class ReportServiceTest {

    @InjectMocks
    private ReportServiceImpl service;

    @Mock
    private ReportsRepository repository;

    @Mock
    private ParseReportService parseReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public String createUuid() {
        return UUID.randomUUID().toString();
    }

    public CSVReader createCsvReader() {
        return new CSVReader(new StringReader(""));
    }

    public Reports createReports() {
        return Reports.builder().build();
    }

    @Test
    public void testGetReportCsv() throws IOException {
        String uuid = createUuid();
        CSVReader reader = createCsvReader();
        String fileId = "";

        when(parseReportService.getReport(fileId, uuid)).thenReturn(reader);

        CSVReader response = service.getReportCsv(fileId, uuid);

        assertNotNull(response);
        assertEquals(reader, response);
    }

    @Test
    public void testGetLatestReport() {
        String uuid = createUuid();
        Reports report = createReports();

        when(repository.findFirstByOrderByGmtCreateDesc()).thenReturn(report);

        Reports response = service.getLatestReport(uuid);
        
        assertNotNull(response);
        assertEquals(report, response);
    }

    @Test
    public void testProcessReport() {
        String uuid = createUuid();
        String reportId = "report";
        Reports reports = createReports();

        when(repository.findByReportId(reportId)).thenReturn(Optional.of(reports));
        
        service.processReport(uuid, reportId);

        verify(repository, times(1)).save(any());
    }
}
