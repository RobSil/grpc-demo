package com.robsil.grpcutil.util;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import model.InstanceServer;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ChannelUtil.class);

    public static ManagedChannel openChannel(EurekaClient eurekaClient, String serviceName) {
        try {
            if (eurekaClient == null) throw new IllegalArgumentException("EurekaClient is null.");
            StringUtil.notEmpty(serviceName, "ServiceName is null or empty.");

            var instanceInfo = getInstanceSortedByLoad(eurekaClient, serviceName);

            int grpcPort = Integer.valueOf(instanceInfo.getMetadata().getOrDefault("grpc-port", "9082"));

            return ManagedChannelBuilder
                    .forAddress(instanceInfo.getHostName(), grpcPort)
                    .usePlaintext()
                    .build();
        } catch (Exception e) {
            logger.error("error occurred during opening channel.");
            logger.error(e.getMessage());

            return null;
        }
    }

    private static InstanceInfo getInstanceSortedByLoad(EurekaClient eurekaClient, String serviceName) {
        var result = getInstancesSortedByLoad(eurekaClient, serviceName);

        if (result.size() == 0) {
            throw new IllegalArgumentException("Not found any instances by name: %s".formatted(serviceName));
        }

        return result.get(0);
    }

    private static List<InstanceInfo> getInstancesSortedByLoad(EurekaClient eurekaClient, String serviceName) {
        List<InstanceInfo> instances = eurekaClient.getApplication(serviceName).getInstances();
        InstanceServer[] preServerArray = new InstanceServer[instances.size()];
        for (int i = 0; i < instances.size(); i++) {
            preServerArray[i] = new InstanceServer(instances.get(i));
        }

//        ServerList<InstanceServer> serverList = new StaticServerList<>(preServerArray);
        var serverList = new InstanceServerList(preServerArray);
        IRule loadBalancerRule = new RoundRobinRule(); // use RoundRobin as the load balancing strategy
        DynamicServerListLoadBalancer<InstanceServer> loadBalancer = LoadBalancerBuilder.<InstanceServer>newBuilder()
                .withDynamicServerList(serverList)
                .withRule(loadBalancerRule)
                .buildDynamicServerListLoadBalancer();

//        List<InstanceInfo> sortedInstances = new ArrayList<>();
//        for (InstanceInfo instance : instances) {
//            DiscoveryEnabledServer server = new DiscoveryEnabledServer((InstanceInfo)instance, true);
//            var server = new Server(instance.getHostName(), instance.getPort());
//            loadBalancer.addServer(server);
//        }

//        for (Server server : loadBalancer.getServerList(true)) {
//            sortedInstances.add(((InstanceServer)server).getInstanceInfo());
//        }

        return loadBalancer
                .getServerListImpl()
                .getUpdatedListOfServers()
                .stream()
                .map(InstanceServer::getInstanceInfo)
                .collect(Collectors.toList());
    }

}
