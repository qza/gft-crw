package org.qza.gft.crw.server;

import java.util.List;

import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.ServerAddresses;

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

	private String reportFileMain;

	private String reportFileQueue;

	private String reportFileVisited;

	private Integer reportLogInterval;

	private ServerAddresses servers;

	private Integer serverMaxclients;

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

	public String getReportFileMain() {
		return reportFileMain;
	}

	public String getReportFileQueue() {
		return reportFileQueue;
	}

	public String getReportFileVisited() {
		return reportFileVisited;
	}

	public void setReportFileMain(String reportFileMain) {
		this.reportFileMain = reportFileMain;
	}

	public void setReportFileQueue(String reportFileQueue) {
		this.reportFileQueue = reportFileQueue;
	}

	public void setReportFileVisited(String reportFileVisited) {
		this.reportFileVisited = reportFileVisited;
	}

	public Integer getReportLogInterval() {
		return reportLogInterval;
	}

	public void setReportLogInterval(String reportLogInterval) {
		this.reportLogInterval = Integer.valueOf(reportLogInterval);
	}

	public Integer getServerMaxclients() {
		return serverMaxclients;
	}

	public void setServerMaxclients(String serverMaxclients) {
		this.serverMaxclients = Integer.valueOf(serverMaxclients);
	}

}
