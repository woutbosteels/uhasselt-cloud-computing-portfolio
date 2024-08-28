package eu.bosteels.wout.kwikbeheer.mqtt;

import eu.bosteels.wout.kwikbeheer.service.TemperatureMeasurementService;
import eu.bosteels.wout.kwikbeheer.websockets.SocketHandler;
import eu.bosteels.wout.kwikbeheer.websockets.TempSocketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

    @Value("${mqtt.hostname}")
    private String mqttHost;


    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(String.format("tcp://%s:1883", mqttHost), "kwik",
                        "+/+/temperature");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler(SocketHandler socketHandler, TempSocketHandler tempSocketHandler, TemperatureMeasurementService temperatureService) {
        return new MqttListener(socketHandler, tempSocketHandler, temperatureService);
    }

}

