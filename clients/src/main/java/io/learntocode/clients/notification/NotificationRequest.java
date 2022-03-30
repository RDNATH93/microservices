package io.learntocode.clients.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest{

			private Integer customerId;
	        private String customerName;
	        private String message;

}


