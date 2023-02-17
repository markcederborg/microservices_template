package dk.dtu.producer;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dk.dtu.dto.CustomMessage;

@Component
@RestController
public class Producer implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;

  public Producer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Sending message...");
  }

  @Autowired
  private RabbitTemplate template;

  @PostMapping("/publish")
  public String publishMessage(@RequestBody CustomMessage message) {
    message.setMessageId(UUID.randomUUID().toString());
    message.setMessageDate(new Date());
    template.convertAndSend(MQconfig.EXCHANGE,
        MQconfig.ROUTING_KEY, message);

    return "Message Published";
  }

  @GetMapping("/{message}")
  public String sendMessage(@PathVariable String message) {
    rabbitTemplate.convertAndSend(MQconfig.EXCHANGE, MQconfig.ROUTING_KEY, message);
    return "Message sent: " + message;
  }

}