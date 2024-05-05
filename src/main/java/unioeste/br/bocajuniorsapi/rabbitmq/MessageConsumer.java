package unioeste.br.bocajuniorsapi.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.dto.SubmissionResultDTO;
import unioeste.br.bocajuniorsapi.service.SubmissionService;
import unioeste.br.bocajuniorsapi.utils.JSONMapper;

import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
public class MessageConsumer {
    private SubmissionService submissionService;
    @RabbitListener(queues = "result_submission_queue")
    public void handleMessage(Message message) {
        try {

            // Extract the message body as a byte array
            byte[] body = message.getBody();
            String str = new String(body, StandardCharsets.UTF_8);
            System.out.println("data: " + str);

            // Deserialize the message body into the SubmissionResult object
            SubmissionResultDTO submissionResult = JSONMapper.objectMapper.readValue(body, SubmissionResultDTO.class);

            // Process the submissionResult object
            System.out.println("Received submission result: " + submissionResult);
            Submission submission = submissionService.findById(submissionResult.getId());

            submissionService.update(submission, submissionResult);

            submissionService.save(submission);

        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

}
