package dk.dtu.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import dk.dtu.dto.CustomMessage;

@Component
public class Consumer {

  private CountDownLatch latch = new CountDownLatch(1);

  public void receiveMessage(String message) {
    System.out.println("Received <" + message + ">");
    latch.countDown();
  }

  @RabbitListener(queues = MQconfig.QUEUE)
  public void listener(CustomMessage message) {
    System.out.println(message);
  }

  public CountDownLatch getLatch() {
    return latch;
  }

}
