package com.project.toy_log_validator.serializers;

import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.toy_log_validator.dto.KafkaDTO;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaMessageSerializer implements Serializer<KafkaDTO>{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, KafkaDTO data) {
        try {
            if(data == null) {
                log.error("given kafka DTO data is null");
                return null;
            }

            log.info("serializing kafka data");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error! serializing KafkaDTO into byte[]");
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, KafkaDTO data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
