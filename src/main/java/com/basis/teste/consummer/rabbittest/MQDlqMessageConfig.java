package com.basis.teste.consummer.rabbittest;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import java.util.HashMap;
import java.util.Map;

public class MQDlqMessageConfig {
    public static final String DLQUEUE = "dlq.reprocessamento";
    public static final String DLQEXCHANGE = "exc.exclusao.dlq";
    public static final String DLQ_ROUTING_KEY = "dlqreproc.#";
    private static final String X_RETRY_HEADER = "x-dlq-retry";

    @Bean
    public Queue queue() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(X_RETRY_HEADER, 3);
        return  new Queue(DLQUEUE, true, false, false, args);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(DLQEXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(DLQ_ROUTING_KEY);
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
