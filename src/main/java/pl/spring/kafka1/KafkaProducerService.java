package pl.spring.kafka1;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class KafkaProducerService {

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    public void sendMessage( String message){
        kafkaTemplate.send("demo-topic", message);
    }
}
