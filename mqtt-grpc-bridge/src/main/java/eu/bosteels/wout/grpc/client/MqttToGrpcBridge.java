package eu.bosteels.wout.grpc.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MqttToGrpcBridge {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MqttToGrpcBridge.class)
                .run(args);
    }
}
