package com.co.code.assistant.providers.mq;

import com.co.code.assistant.core.repositories.mq.ISuggestionMQRepository;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.reactivex.rxjava3.core.Observable;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQ implements ISuggestionMQRepository<Observable<List<ISuggestionDto>>, Map<String, String>> {
    private String QUEUE_NAME = "CHANNEL_DIEGO";
    ConnectionFactory factory = new ConnectionFactory();

    public RabbitMQ()  {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received Rabbit '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {

        }

    }

    @Override
    public void setInformation(Map<String, String> info) {

        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
             channel.queueDeclare(QUEUE_NAME, false, false, false, null);
             String message = "Hello World!";
             channel.basicPublish("", QUEUE_NAME, null, info.get("code").getBytes());
             System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
