package com.project.toy_log_validator.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.project.toy_log_validator.tasks.ParseReportTasklet;
import com.project.toy_log_validator.tasks.ValidateDataTasklet;
import com.project.toy_log_validator.tasks.ValidateSchemaTasklet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ComponentScan("com.project.toy_log_validator.tasks")
public class ValidatorConfig {
        @Autowired
        private ValidateSchemaTasklet schemaTasklet;

        @Autowired
        private ValidateDataTasklet dataTasklet;

        @Autowired
        private ParseReportTasklet reportTasklet;

        @Bean("stepValidationSchema")
        public Step validateSchema(@Autowired JobRepository repository,
                        @Autowired PlatformTransactionManager transactionManager) {
                log.info("Creating step (validation schema)...");

                return new StepBuilder("stepValidationSchema", repository)
                                .tasklet(schemaTasklet, transactionManager)
                                .build();
        }

        @Bean("stepValidationData")
        public Step validateData(@Autowired JobRepository repository,
                        @Autowired PlatformTransactionManager transactionManager) {
                log.info("Creating step (validation data)...");

                return new StepBuilder("stepValidationData", repository)
                                .tasklet(dataTasklet, transactionManager)
                                .build();
        }

        @Bean("stepProcessReport")
        public Step processReport(@Autowired JobRepository repository,
                        @Autowired PlatformTransactionManager transactionManager) {
                log.info("Creating step (process report)...");

                return new StepBuilder("stepProcessReport", repository)
                                .tasklet(reportTasklet, transactionManager)
                                .build();
        }

        @Bean("validation")
        public Job validateJob(@Autowired JobRepository repository,
                        @Autowired @Qualifier("stepValidationSchema") Step validateSchemaStep,
                        @Autowired @Qualifier("stepValidationData") Step validateDataStep,
                        @Autowired @Qualifier("stepProcessReport") Step processReportStep) {
                log.info("Creating job (validation)...");

                return new JobBuilder("validation", repository)
                                .incrementer(new RunIdIncrementer())
                                .start(validateSchemaStep)
                                .next(validateDataStep)
                                .next(processReportStep)
                                .build();
        }
}
