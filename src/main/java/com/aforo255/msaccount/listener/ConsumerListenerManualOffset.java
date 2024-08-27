package com.aforo255.msaccount.listener;

import com.aforo255.msaccount.model.domain.Transaction;
import com.aforo255.msaccount.service.IAccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.adapter.rxjava.RxJava2Adapter;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class ConsumerListenerManualOffset implements CommandLineRunner {


    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "transaction-events";

    private final ReceiverOptions<Integer, String> receiverOptions;
    private final SimpleDateFormat dateFormat;

	private IAccountService accountService;

	public ConsumerListenerManualOffset(IAccountService accountService) {
        this.accountService = accountService;

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "sample-consumer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "transaction-events-account");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        receiverOptions = ReceiverOptions.create(props);
        dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");
    }

    public void consumeMessages() {

        ReceiverOptions<Integer, String> options = receiverOptions.subscription(Collections.singleton(TOPIC))
                .addAssignListener(partitions -> log.debug("onPartitionsAssigned {}", partitions))
                .addRevokeListener(partitions -> log.debug("onPartitionsRevoked {}", partitions));
        Flux<ReceiverRecord<Integer, String>> kafkaFlux = KafkaReceiver.create(options).receive();
        kafkaFlux.as(RxJava2Adapter::fluxToFlowable)
                .flatMapCompletable(receiverRecord -> {

                    ReceiverOffset offset = receiverRecord.receiverOffset();

                    log.debug("Received message: topic-partition={} offset={} timestamp={} key={} value={}",
                            offset.topicPartition(),
                            offset.offset(),
                            dateFormat.format(new Date(receiverRecord.timestamp())),
                            receiverRecord.key(),
                            receiverRecord.value()
                    );

                    offset.acknowledge();

                    Transaction transaction = parseTransaction(receiverRecord);

					new CountDownLatch(transaction.getId()).countDown();

                    return accountService.save(transaction);
                })
				.subscribe();
    }

    private Transaction parseTransaction(ReceiverRecord<Integer, String> receiverRecord) {
        Transaction transaction = null;
        try {
            transaction = MAPPER.readValue(receiverRecord.value(), Transaction.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public void run(String... args) throws Exception {
        consumeMessages();
    }
}
