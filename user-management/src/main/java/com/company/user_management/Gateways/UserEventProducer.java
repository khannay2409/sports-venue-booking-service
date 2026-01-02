package com.company.user_management.Gateways;

import com.company.user_management.dto.request.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UserEventProducer {

    private static final String TOPIC = "user-events";

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void publishUserRegistered(Long userId, String email) {
        UserEvent event = new UserEvent(
                "USER_REGISTERED",
                userId,
                email,
                Instant.now()
        );
        kafkaTemplate.send(TOPIC, event);
    }

    public void publishUserLoggedIn(Long userId, String email) {
        UserEvent event = new UserEvent(
                "USER_LOGGED_IN",
                userId,
                email,
                Instant.now()
        );
        kafkaTemplate.send(TOPIC, event);
    }
}
