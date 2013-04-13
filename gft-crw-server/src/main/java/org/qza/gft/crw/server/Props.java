package org.qza.gft.crw.server;

import java.util.List;

import org.qza.gft.crw.address.ServerAddress;
import org.qza.gft.crw.address.ServerAddresses;

/**
 * @author gft
 */
public class Props {

	private Integer queueMaxsize;

	private String queueInitfile;

	private Integer visitedMaxsize;

	private Boolean logTime;

	private Boolean logCompleted;

	private Integer serverTimeout;
	
	private Integer serverDuration;

	private String serverHost;

	private String serverPorts;

	private Integer tpoolInitsize;

	private Integer tpoolMaxsize;

	private ServerAddresses servers;

	public Boolean getLogTime() {
		return logTime;
	}

	public Integer getQueueMaxsize() {
		return queueMaxsize;
	}

	public void setQueueMaxsize(String queueMaxsize) {
		this.queueMaxsize = Integer.valueOf(queueMaxsize);
	}

	public String getQueueInitfile() {
		return queueInitfile;
	}

	public void setQueueInitfile(String queueInitfile) {
		this.queueInitfile = queueInitfile;
	}

	public Integer getVisitedMaxsize() {
		return visitedMaxsize;
	}

	public void setVisitedMaxsize(String visitedMaxsize) {
		this.visitedMaxsize = Integer.valueOf(visitedMaxsize);
	}

	public void setLogTime(String logTime) {
		this.logTime = Boolean.valueOf(logTime);
	}

	public Boolean getLogCompleted() {
		return logCompleted;
	}

	public void setLogCompleted(String logCompleted) {
		this.logCompleted = Boolean.valueOf(logCompleted);
	}

	public Integer getServerTimeout() {
		return serverTimeout;
	}

	public void setServerTimeout(String serverTimeout) {
		this.serverTimeout = Integer.valueOf(serverTimeout);
	}
	
	public Integer getServerDuration() {
		return serverDuration;
	}
	
	public void setServerDuration(String serverDuration) {
		this.serverDuration = Integer.valueOf(serverDuration);
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getServerPorts() {
		return serverPorts;
	}

	public void setServerPorts(String serverPorts) {
		this.serverPorts = serverPorts;
		if (this.serverHost != null) {
			this.servers = new ServerAddresses(serverHost, serverPorts);
		}
	}

	public List<ServerAddress> getServerList() {
		return this.servers.getList();
	}

	public Integer getTpoolInitsize() {
		return tpoolInitsize;
	}

	public void setTpoolInitsize(String tpoolInitsize) {
		this.tpoolInitsize = Integer.valueOf(tpoolInitsize);
	}

	public Integer getTpoolMaxsize() {
		return tpoolMaxsize;
	}

	public void setTpoolMaxsize(String tpoolMaxsize) {
		this.tpoolMaxsize = Integer.valueOf(tpoolMaxsize);
	}

}
