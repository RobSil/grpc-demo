package com.robsil.grpcutil.util;

import com.netflix.loadbalancer.AbstractServerList;
import model.InstanceServer;

import java.util.List;

public class InstanceServerList extends AbstractServerList<InstanceServer> {

    private final InstanceServer[] instanceServers;

    public InstanceServerList(InstanceServer[] instanceServers) {
        this.instanceServers = instanceServers;
    }

    @Override
    public List<InstanceServer> getInitialListOfServers() {
        return List.of(instanceServers);
    }

    @Override
    public List<InstanceServer> getUpdatedListOfServers() {
        return List.of(instanceServers);
    }
}
