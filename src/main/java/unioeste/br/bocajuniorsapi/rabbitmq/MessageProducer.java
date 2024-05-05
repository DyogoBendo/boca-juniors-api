package unioeste.br.bocajuniorsapi.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageProducer {
    private RabbitTemplate rabbitTemplate;

    @Bean
    public Queue queue() {
        return new Queue("result_submission_queue");
    }

    public void sendMessage(String body) {
        MessageProperties properties = new MessageProperties();
        properties.setReplyTo("result_submission_queue");
        Message message = new Message(body.getBytes(), properties);
        rabbitTemplate.send("submission_queue", message);
        System.out.println("Message sent: " + message);
        try{
            System.out.println("message sent content: " + new String(message.getBody(), "UTF-8"));
        } catch (Exception e){
            System.out.println(e);
        }

    }
}
