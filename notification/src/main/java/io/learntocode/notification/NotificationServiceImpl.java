package io.learntocode.notification;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import io.learntocode.clients.notification.NotificationRequest;
import io.learntocode.clients.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void send(NotificationRequest notificationRequest) {
    	//Object notificationRequest = SerializationUtils.deserialize(Request);
    	 log.info("New notification... {}", notificationRequest);
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.getCustomerId())
                        .toCustomerEmail(notificationRequest.getCustomerName())
                        .sender("Amigoscode")
                        .message(notificationRequest.getMessage())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}