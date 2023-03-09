package com.robsil.greetingclient.service;

import com.robsil.lib.proto.GreetingGrpc;
import com.robsil.lib.proto.GreetingRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @GrpcClient("greeting-grpc-client")
    private GreetingGrpc.GreetingBlockingStub greetingStub;

    public String getGreeting(String name) {
        return greetingStub.greet(GreetingRequest.newBuilder().setName(name).build()).getMessage();
    }

}
