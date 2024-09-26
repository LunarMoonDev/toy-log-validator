package com.project.toy_log_validator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.project.toy_log_validator.dto.KafkaDTO;
import com.project.toy_log_validator.service.KafkaProducerService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, KafkaDTO> kafkaTemplate;

    @Value("${kafka.log.validation.topic.name}")
    @Setter
    private String topicName;

    @Override
    public void sendSuccessMessage(String uuid, KafkaDTO payload) {
        log.info("X-Tracker: {} | publishing to kafka broker", uuid);
        log.debug("X-Tracker: {} | topic: {} | payload: {}", uuid, topicName, payload);

        kafkaTemplate.send(topicName, payload);
    }
}
