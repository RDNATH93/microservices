package io.learntocode.fraud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.learntocode.clients.fraud.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public class FraudController {
	
	private final FraudCheckService fraudCheckService;
	
	public FraudController(FraudCheckService fraudCheckService) {
		this.fraudCheckService=fraudCheckService;
	}
	
	@GetMapping("{customerId}")
    public FraudCheckResponse isFradster(@PathVariable("customerId")Integer customerId) {
		log.info("fraud check for customer {}",customerId);
    	boolean isFradulentCustomer = fraudCheckService.isFradulentCustomer(customerId);
    	return new FraudCheckResponse(isFradulentCustomer);
    }
}
