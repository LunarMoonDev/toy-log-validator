package com.project.toy_log_validator.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import com.project.toy_log_validator.dto.KafkaDTO;
import com.project.toy_log_validator.service.impl.KafkaProducerServiceImpl;

public class KafkaProducerServiceTest {
    
    @InjectMocks
    private KafkaProducerServiceImpl service;

    @Mock
    private KafkaTemplate<String, KafkaDTO> kafkaTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service.setTopicName("my.topic");
    }

    public String createUuid() {
        return UUID.randomUUID().toString();
    }

    public KafkaDTO createKafkaDTO() {
        return KafkaDTO.builder().build();
    }

    @Test
    public void testSendSuccessMessage() {
        String uuid = createUuid();
        KafkaDTO payload = createKafkaDTO();

        service.sendSuccessMessage(uuid, payload);

        verify(kafkaTemplate, times(1)).send(anyString(), any());
    }
}
