package org.qza.gft.crw.address;

/**
 * @author gft
 * 
 */
public class ServerAddress {

	private String host;
	private Integer port;

	public ServerAddress(String hostPort) {
		String[] data = hostPort.split(":");
		this.host = data[0];
		this.port = Integer.valueOf(data[1]);
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}
}
