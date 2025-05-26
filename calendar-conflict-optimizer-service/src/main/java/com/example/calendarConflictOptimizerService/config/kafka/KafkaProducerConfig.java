package com.example.calendarConflictOptimizerService.config.kafka;

import com.example.calendarConflictOptimizerService.dto.Calendar;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
public class KafkaProducerConfig {


    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerializer.class);
        return props;
    }

    @Bean("kafkaMessage")
    public ProducerFactory<String, Calendar> producerFactoryEvent() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean("kafkaTemplate")
    public KafkaTemplate<String, Calendar> kafkaTemplateEvent(@Autowired @Qualifier("kafkaMessage") ProducerFactory<String, Calendar> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
