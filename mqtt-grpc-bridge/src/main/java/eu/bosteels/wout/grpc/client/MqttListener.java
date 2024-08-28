package eu.bosteels.wout.grpc.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bosteels.wout.grpc.TemperatureRequest;
import eu.bosteels.wout.grpc.TemperatureServiceGrpc;
import eu.bosteels.wout.grpc.mqtt.TemperatureMeasurementDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class MqttListener implements MessageHandler {

    private static final Logger log = LogManager.getLogger(MqttConfig.class);

    @Autowired
    TemperatureServiceGrpc.TemperatureServiceBlockingStub temperatureServiceBlockingStub;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        log.debug(message.getHeaders());
        log.debug(message.getPayload());
        TemperatureMeasurementDTO dto = convertToTemperatureMeasurementDTO(message.getPayload());
        log.debug("dto: {}", dto.toString());

        // convert from JSON to dto to gRPC

        var request = TemperatureRequest.newBuilder()
                .setBuilding(dto.getBuilding())
                .setRoom(dto.getRoom())
                .setCelsius(dto.getCelsius())
                .build();

        try {
            var response = temperatureServiceBlockingStub.hello(request);
            log.info("gRPC server responded: {}", response);
        } catch (Exception e) {
            log.warn("Failed to contact gRPC server: {}", e.getMessage());
        }

    }

    private TemperatureMeasurementDTO convertToTemperatureMeasurementDTO(Object payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(payload.toString(), TemperatureMeasurementDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse json in MQTT message", e);
        }
    }

}
