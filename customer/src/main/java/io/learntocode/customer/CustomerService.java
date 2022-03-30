package io.learntocode.customer;

import org.springframework.stereotype.Service;

import io.learntocode.amqp.RabbitMQMessageProducer;
import io.learntocode.clients.fraud.FraudCheckResponse;
import io.learntocode.clients.fraud.FraudClient;
import io.learntocode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService {
	
 
	private final CustomerRepository customerRepository;
	private final FraudClient fraudClient;
	private final RabbitMQMessageProducer rabbitMQMessageProducer;
	
	//@Autowired
//	private  RestTemplate restTemplate;
   
	public void registerCustomer(CustomerRegistrationRequest request) {
	
		//builder pattern
		Customer customer=Customer.builder()
								  .firstName(request.getFirstName())
								  .lastName(request.getLastName())
								  .email(request.getEmail())
								  .build();
		
		//todo: check if email is valid
		//todo: check if mail is taken
		
		//store customer in db
		customerRepository.saveAndFlush(customer);
		
		//check if a fraud request
//		FraudCheckResponse response = restTemplate.getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}",
//									FraudCheckResponse.class ,customer.getId());
		
		FraudCheckResponse response=fraudClient.isFraudster(customer.getId());
				
		if(response!=null && response.isFradulentCustomer()) {
			throw new IllegalArgumentException("fradulent customer!");
		}
		

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to LearnToCode...",
                        customer.getFirstName())
        );
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
        
	}

}
