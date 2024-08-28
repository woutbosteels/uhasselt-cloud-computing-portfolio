package eu.bosteels.wout.server;

import eu.bosteels.wout.grpc.TemperatureRequest;
import eu.bosteels.wout.grpc.TemperatureResponse;
import eu.bosteels.wout.grpc.TemperatureServiceGrpc.TemperatureServiceImplBase;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TemperatureServiceImpl extends TemperatureServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureServiceImpl.class);

    @Override
    public void hello(
            TemperatureRequest request, StreamObserver<TemperatureResponse> responseObserver) {

        //System.out.println("Request received from client:\n" + request);

        String summary = "Ok, we noted it is now "
                + request.getCelsius() + " degrees celsius"
                + " in room: "   +     request.getRoom()
                + " of building " + request.getBuilding();

        logger.info(summary);

        TemperatureResponse response = TemperatureResponse.newBuilder()
                .setSummary(summary)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
