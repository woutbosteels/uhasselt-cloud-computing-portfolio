package eu.bosteels.wout.kwikbeheer.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bosteels.wout.kwikbeheer.service.TemperatureMeasurementService;
import eu.bosteels.wout.kwikbeheer.websockets.SocketHandler;
import eu.bosteels.wout.kwikbeheer.websockets.TempSocketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.FormatStyle.*;

@Component
public class MqttListener implements MessageHandler  {

    private static final Logger log = LogManager.getLogger(MqttListener.class);

    private final SocketHandler socketHandler;
    private final TempSocketHandler tempSocketHandler;
    private final TemperatureMeasurementService temperatureService;

    @Autowired
    public MqttListener(SocketHandler socketHandler, TempSocketHandler tempSocketHandler, TemperatureMeasurementService temperatureService) {
        this.socketHandler = socketHandler;
        this.tempSocketHandler = tempSocketHandler;
        this.temperatureService = temperatureService;
    }

    @ServiceActivator(inputChannel = "mqttInputChannel")
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        log.debug(message.getHeaders());
        log.debug(message.getPayload());
        TemperatureMeasurementDTO dto = convertToTemperatureMeasurementDTO(message.getPayload());
        log.debug("dto: {}", dto.toString());

        // TODO: pass dto to socketHandler and let it generate a nicer formatted html message
        LocalDateTime now = LocalDateTime.now();
        String ts = now.format(DateTimeFormatter.ofLocalizedTime(MEDIUM));
        int temp = (int) dto.getCelsius();


        temperatureService.saveTemperatureMeasurement(dto.getBuilding(), dto.getRoom(), dto.getCelsius(), now);

        String chatMessage =
                ts + " : " +
                "It is " + temp + "° Celsius in the " + dto.getRoom() + " at " + dto.getBuilding() + ".";
        socketHandler.sendMessageToChatRoom(chatMessage);
        tempSocketHandler.tempToRoom(ts, dto.getCelsius(), dto.getBuilding(), dto.getRoom());

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
