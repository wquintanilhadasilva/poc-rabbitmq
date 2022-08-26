package com.basis.teste.consummer.rabbittest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class MessageDLQListener {
    private static Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @RabbitListener(queues = MQDlqMessageConfig.DLQUEUE)
    public void listener(Message message) throws Exception {
        LocalDateTime lt = LocalDateTime.now();
        logger.debug(lt.toString());
        //System.out.println("Reprocessando mensagem: " + message.getMessage());
        throw new Exception("Não consuma a mensagem 2!");
    }
}
