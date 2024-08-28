package eu.bosteels.wout.grpc.client;

import eu.bosteels.wout.grpc.TemperatureServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    public static final int GRPC_SERVER_PORT = 9090;

    private final String grpcHost = System.getenv("GRPC_HOSTNAME");

    @Bean
    public TemperatureServiceGrpc.TemperatureServiceBlockingStub stub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcHost, GRPC_SERVER_PORT)
                .usePlaintext().build();
        return TemperatureServiceGrpc.newBlockingStub(channel);

    }
}
