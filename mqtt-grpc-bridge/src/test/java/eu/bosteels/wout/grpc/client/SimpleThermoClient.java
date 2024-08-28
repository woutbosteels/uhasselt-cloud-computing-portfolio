package eu.bosteels.wout.grpc.client;

import eu.bosteels.wout.grpc.TemperatureRequest;
import eu.bosteels.wout.grpc.TemperatureServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SimpleThermoClient {
    public static final int PORT = 9090;


    public static void main(String[] args) {

        System.setProperty("slf4j.internal.verbosity", "WARN");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", PORT)
                .usePlaintext().build();

        TemperatureServiceGrpc.TemperatureServiceBlockingStub stub1
                = TemperatureServiceGrpc.newBlockingStub(channel);

        var request = TemperatureRequest.newBuilder()
                .setBuilding("Hogwarts")
                .setRoom("Harry Potter")
                .setCelsius(17.8f)
                .build();

        var response = stub1.hello(request);
        System.out.println(response);
        System.out.println("Response received from server:\n" + response);

        channel.shutdown();

    }
}

