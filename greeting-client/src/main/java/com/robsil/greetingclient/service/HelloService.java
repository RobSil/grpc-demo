package com.robsil.greetingclient.service;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.robsil.grpcutil.util.ChannelUtil;
import com.robsil.lib.proto.GreetingGrpc;
import com.robsil.lib.proto.GreetingRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelloService {

    private final EurekaClient eurekaClient;

    @GrpcClient("greeting-grpc-client")
    private GreetingGrpc.GreetingBlockingStub greetingStub = null;

//    @PostConstruct
//    void init() {
//        greetingStub = GreetingGrpc.newBlockingStub(ChannelUtil.openChannel(eurekaClient, "greeting-service"));
//    }

    public String getGreeting(String name) {
        return greetingStub.greet(GreetingRequest.newBuilder().setName(name).build()).getMessage();
    }

}
