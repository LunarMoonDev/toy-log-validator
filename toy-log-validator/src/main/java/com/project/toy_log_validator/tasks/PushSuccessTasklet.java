package com.project.toy_log_validator.tasks;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.toy_log_validator.dto.KafkaDTO;
import com.project.toy_log_validator.enums.Validation;
import com.project.toy_log_validator.service.KafkaProducerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@JobScope
public class PushSuccessTasklet implements Tasklet {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Value("#{jobParameters['uuid']}")
    private String uuid;
    
    @Value("#{jobParameters['reportId']}")
    private String reportId;

    @Value("#{jobParameters['playerId']}")
    private long playerId;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("X-Tracker: {} | task one: pushing success message to broker", uuid);

        KafkaDTO payload = KafkaDTO.builder()
            .code(Validation.SUCCESS.getCode())
            .message(Validation.SUCCESS.getMessage())
            .reportId(reportId)
            .playerId(playerId)
        .build();

        kafkaProducerService.sendSuccessMessage(uuid, payload);
        return RepeatStatus.FINISHED;
    }
}
