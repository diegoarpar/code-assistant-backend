package com.co.code.assistant.providers.mq;

import com.co.code.assistant.core.repositories.mq.ISuggestionMQRepository;
import com.co.code.assistant.providers.items.dto.ISuggestionDto;
import com.rabbitmq.client.ConnectionFactory;
import io.reactivex.rxjava3.core.Observable;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Kafka implements ISuggestionMQRepository<Observable<List<ISuggestionDto>>, Map<String, String>> {
    private String QUEUE_NAME = "CHANNEL_DIEGO";
    ConnectionFactory factory = new ConnectionFactory();

    public Kafka() throws Exception {
        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "my-fifth-application";
        String topic = "demo_java";

        // create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // get a reference to the current thread
        final Thread mainThread = Thread.currentThread();

        // adding the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Detected a shutdown, let's exit by calling consumer.wakeup()...");
                consumer.wakeup();

                // join the main thread to allow the execution of the code in the main thread
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


          Thread one =  new Thread() {
                public void run() {
                    try {
                    // subscribe consumer to our topic(s)
                    consumer.subscribe(Arrays.asList(topic));

                    // poll for new data
                    while (true) {
                        ConsumerRecords<String, String> records =
                                consumer.poll(Duration.ofMillis(100));

                        for (ConsumerRecord<String, String> record : records) {
                            System.out.println("Message received kafka");
                            System.out.println(("Key: " + record.key() + ", Value: " + record.value()));
                            System.out.println(("Partition: " + record.partition() + ", Offset:" + record.offset()));
                        }
                    }

                } catch(
                WakeupException e)

                {
                    System.out.println(("Wake up exception!" + e.getMessage()));
                    // we ignore this as this is an expected exception when closing a consumer
                } catch(
                Exception e)

                {
                    System.out.println("Unexpected exception" + e.getMessage());
                } finally

                {
                    consumer.close(); // this will also commit the offsets if need be.
                    System.out.println("The consumer is now gracefully closed.");
                }
            }};
        one.start();
    }

    @Override
    public void setInformation(Map<String, String> info) {



        String bootstrapServers = "127.0.0.1:9092";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // create a producer record
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>("demo_java", info.get("code"));
        System.out.println("messagen sent" + info.get("code"));
        // send data - asynchronous
        producer.send(producerRecord);

        // flush data - synchronous
        producer.flush();
        // flush and close producer
        producer.close();
    }
}
