package com.basis.teste.consummer.rabbittest;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQMessageConfig {
    public static final String QUEUE = "q.mensagem";
    public static final String EXCHANGE = "exc.mensagem";
    public static final String ROUTING_KEY = "mensagem.#";

    @Bean
    public Queue queue() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", "exc.mensagem.dlq");
        args.put("x-dead-letter-routing-key", "dlqmensagem.#");

        return  new Queue(QUEUE, true, false, false, args);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }
}
