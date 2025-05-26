package com.example.calendarConflictOptimizerService.config.kafka;

import com.example.calendarConflictOptimizerService.dto.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {

    @Value("${kafka.send.topic}")
    String topic;

    @Autowired
    @Qualifier("kafkaTemplate")
    KafkaTemplate<String, Calendar> kafkaTemplate;

    public void send(Calendar calendar) {
        kafkaTemplate.send(topic, calendar);
    }

}