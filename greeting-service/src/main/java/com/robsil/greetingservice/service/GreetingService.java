package com.robsil.greetingservice.service;

import com.robsil.lib.proto.GreetingGrpc;
import com.robsil.lib.proto.GreetingRequest;
import com.robsil.lib.proto.GreetingResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreetingService extends GreetingGrpc.GreetingImplBase {

    @Override
    public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {

        var response = GreetingResponse.newBuilder()
                .setMessage("Hello, " + request.getName() + "!")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
