package org.qza.gft.crw.address;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gft
 */
public class ServerAddresses {

	private List<ServerAddress> addressList;

	public ServerAddresses(String serverHosts) {
		if (addressList == null) {
			addressList = new ArrayList<>();
			String[] servers = serverHosts.split(",");
			for (int i = 0; i < servers.length; i++) {
				addressList.add(new ServerAddress(servers[i]));
			}
		}
	}

	public ServerAddresses(String host, String ports) {
		if (addressList == null) {
			addressList = new ArrayList<>();
			String[] serverPorts = ports.split(",");
			for (int i = 0; i < serverPorts.length; i++) {
				addressList.add(new ServerAddress(host + ":" + serverPorts[i]));
			}
		}
	}

	public List<ServerAddress> getList() {
		return addressList;
	}

}
