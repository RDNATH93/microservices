package io.learntocode.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

//@Configuration
public class CustomerConfig {
	
	@Value("${spring.rabbitmq.addresses}")
	private String rabbitmqAddress;
	
	//@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	//@Bean
//    public CachingConnectionFactory getConnectionFactory() {
//    	CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//    	cachingConnectionFactory.setCacheMode(CacheMode.CHANNEL);
//    	cachingConnectionFactory.setAddresses(rabbitmqAddress);
//    	return cachingConnectionFactory;
//    }
}
