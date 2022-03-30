package io.learntocode.notification;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.learntocode.clients.notification.NotificationService;

@Configuration
public class NotificationConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(this.notificationQueue);
    }

    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(this.internalNotificationRoutingKey);
    }


    public String getInternalExchange() {
        return internalExchange;
    }

    public String getNotificationQueue() {
        return notificationQueue;
    }

    public String getInternalNotificationRoutingKey() {
        return internalNotificationRoutingKey;
    }
    
    @Bean
    public MessageListenerAdapter listenerAdapter(NotificationService receiver, MessageConverter jacksonConverter) {
       MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "send");
       messageListenerAdapter.setMessageConverter(jacksonConverter);
       return messageListenerAdapter;
    }
    
	@Bean
	public SimpleMessageListenerContainer  simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer  factory = new SimpleMessageListenerContainer ();
		factory.setConnectionFactory(connectionFactory);
		factory.setQueueNames(this.notificationQueue);
		factory.setMessageListener(listenerAdapter);
		// factory.setMessageConverter(jacksonConverter());
		// factory.setChannelTransacted(true);
		// factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
		return factory;
	}
}