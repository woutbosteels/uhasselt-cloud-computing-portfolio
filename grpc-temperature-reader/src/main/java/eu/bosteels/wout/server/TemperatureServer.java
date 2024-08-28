package eu.bosteels.wout.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class TemperatureServer {

    public static final int PORT = 9090;

    public static void main(String[] args) throws IOException, InterruptedException {

        System.setProperty("slf4j.internal.verbosity", "WARN");

        Server server = ServerBuilder.forPort(PORT)
                .addService(new TemperatureServiceImpl()).build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started on PORT " + PORT);
        server.awaitTermination();
        System.out.println("Server stopped!");

    }

}
