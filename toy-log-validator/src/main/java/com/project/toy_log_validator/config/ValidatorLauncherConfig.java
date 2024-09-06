package com.project.toy_log_validator.config;

import org.apache.kafka.common.Uuid;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.opencsv.CSVReader;
import com.project.toy_log_validator.entity.Reports;
import com.project.toy_log_validator.service.ReportService;
import com.project.toy_log_validator.utils.WorkbookUtil;

import lombok.extern.slf4j.Slf4j;
import java.math.BigInteger;

@Slf4j
@Configuration
@EnableScheduling
public class ValidatorLauncherConfig {
    @Autowired
    private ReportService reportService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void runBatchJob() {
        try {
            String uuid = Uuid.randomUuid().toString();
            Reports reports = reportService.getLatestReport(uuid);
            BigInteger playerId = reports.getPlayerId();
            String reportId = reports.getReportId();

            CSVReader report = reportService.getReportCsv(reports.getReportId(), uuid);
            int[] sizes = WorkbookUtil.getDimensions(report);

            if (!reports.getIsProcessed()) {
                JobParameters parameter = new JobParametersBuilder()
                        .addString("uuid", uuid)
                        .addString("reportId", reportId)
                        .addLong("playerId", playerId.longValue())
                        .addLong("timestamp", System.currentTimeMillis())
                        .addDouble("colSize", sizes[0] + 0.0)
                        .addDouble("rowSize", sizes[1] + 0.0)
                        .toJobParameters();

                jobLauncher.run(job, parameter);
            }
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
        }
    }
}
