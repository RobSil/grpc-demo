package model;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.Server;

public class InstanceServer extends Server {

    private InstanceInfo instanceInfo;

    public InstanceServer(InstanceInfo instanceInfo) {
        super(instanceInfo.getHostName(), instanceInfo.getPort());
        this.instanceInfo = instanceInfo;
    }

    public InstanceInfo getInstanceInfo() {
        return instanceInfo;
    }
}
