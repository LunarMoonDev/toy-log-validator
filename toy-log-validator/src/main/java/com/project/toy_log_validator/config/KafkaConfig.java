package com.project.toy_log_validator.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.project.toy_log_validator.dto.KafkaDTO;
import com.project.toy_log_validator.serializers.KafkaMessageSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Value("${spring.kafka.producer.configuration.acks}")
    private String kafkaProducerConfigurationAcks;

    @Value("${spring.kafka.producer.configuration.enable-idempotence}")
    private String kafkaProducerConfigurationEnableIdempotence;

    @Value("${spring.kafka.producer.configuration.add-type-information-headers}")
    private String kafkaProducerConfigurationAddTypeInformationHeaders;

    @Value("${spring.kafka.api.key}")
    private String kafkaApiKey;

    @Value("${spring.kafka.api.secret}")
    private String kafkaApiSecret;

    @Value("${spring.kafka.security.protocol}")
    private String kafkaSecurityProtocol;

    @Value("${spring.kafka.sasl.jaas}")
    private String kafkaSaslJaas;

    @Value("${spring.kafka.sasl.mechanism}")
    private String kafkaSaslMechnism;

    @Value("${spring.kafka.client.dns.lookup}")
    private String kafkaClientDnsLookup;

    @Value("${kafka.log.validation.topic.name}")
    private String topicName;

    @Value("${kafka.log.topic.partition}")
    private Integer partition;

    @Value("${kafka.log.topic.replication.factor}")
    private Integer replicationFactor;

    public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaMessageSerializer.class);
        properties.put(ProducerConfig.ACKS_CONFIG, kafkaProducerConfigurationAcks);
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, kafkaProducerConfigurationEnableIdempotence);

        if (this.kafkaApiKey.isEmpty() == false && this.kafkaApiSecret.isEmpty() == false) {
            properties.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, kafkaSecurityProtocol);

            String saslJaas = String.format("%s required username='%s' password='%s';",
                    kafkaSaslJaas, kafkaApiKey, kafkaApiSecret);
            
            properties.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaas);
            properties.put(SaslConfigs.SASL_MECHANISM, kafkaSaslMechnism);
            properties.put(AdminClientConfig.CLIENT_DNS_LOOKUP_CONFIG, kafkaClientDnsLookup);
        }

        return properties;
    }

    @Bean("kafkaAdmin")
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();

        configs.put(AdminClientConfig.BOOTSTRAP_CONTROLLERS_CONFIG, kafkaBootstrapServers);
        configs.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, kafkaSecurityProtocol);
        configs.put(SaslConfigs.SASL_MECHANISM, kafkaSaslMechnism);

        String saslJaas = String.format("%s required username='%s' password='%s';", kafkaSaslJaas, kafkaApiKey, kafkaApiSecret);

        configs.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaas);
        return new KafkaAdmin(configs);
    }

    @Bean
    public ProducerFactory<String, KafkaDTO> factory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, KafkaDTO> kafkaTemplate(ProducerFactory<String, KafkaDTO> factory) {
        return new KafkaTemplate<>(factory); 
    }
}
