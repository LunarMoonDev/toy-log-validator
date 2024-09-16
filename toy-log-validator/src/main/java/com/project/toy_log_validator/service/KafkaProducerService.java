package com.project.toy_log_validator.service;

import com.project.toy_log_validator.dto.KafkaDTO;

public interface KafkaProducerService {
    void sendSuccessMessage(String uuid, KafkaDTO payload);
}
