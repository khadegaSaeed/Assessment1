package com.kafkaconsumer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafkaconsumer.dto.Calendar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListeners {



    @KafkaListener(topics = "calender-topic", groupId = "calender-topic_groupId")
    public void listenSOMResponse(@Payload String object) {

        log.info("service received : " + object + " ");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            Calendar calendar = objectMapper.readValue(object, Calendar.class);

            log.info(calendar.getTimeZone());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
