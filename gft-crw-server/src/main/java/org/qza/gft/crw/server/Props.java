package org.qza.gft.crw.server;

import java.util.List;

import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.ServerAddresses;

/**
 * @author gft
 */
public class Props {

	private Integer queueMaxsize;

	private Integer visitedMaxsize;

	private Boolean logTime;

	private Boolean logCompleted;

	private Integer serverDuration;

	private String serverHost;

	private String serverPorts;

	private Integer tpoolInitsize;

	private Integer tpoolMaxsize;

	private String reportFilename;

	private Integer reportLogInterval;

	private ServerAddresses servers;

	private Integer serverMaxclients;
	
	private String dataFileQueue;
	
	private String dataFileVisited;
	
	private String dataFileProducts;
	
	private Integer dataPersistInterval;
	
	private Integer dataMemoryMin;
	
	private String[] banCategories;

	public Boolean getLogTime() {
		return logTime;
	}

	public Integer getQueueMaxsize() {
		return queueMaxsize;
	}

	public void setQueueMaxsize(String queueMaxsize) {
		this.queueMaxsize = Integer.valueOf(queueMaxsize);
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

	public String getReportFilename() {
		return reportFilename;
	}

	public void setReportFilename(String reportFilename) {
		this.reportFilename = reportFilename;
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
	
	public String getDataFileVisited() {
		return dataFileVisited;
	}
	
	public String getDataFileQueue() {
		return dataFileQueue;
	}
	
	public String getDataFileProducts() {
		return dataFileProducts;
	}
	
	public void setDataFileVisited(String dataFileVisited) {
		this.dataFileVisited = dataFileVisited;
	}
	
	public void setDataFileQueue(String dataFileQueue) {
		this.dataFileQueue = dataFileQueue;
	}
	
	public void setDataFileProducts(String dataFileProducts) {
		this.dataFileProducts = dataFileProducts;
	}
	
	public void setDataPersistInterval(String dataPersistInterval) {
		this.dataPersistInterval = Integer.valueOf(dataPersistInterval);
	}
	
	public Integer getDataPersistInterval() {
		return dataPersistInterval;
	}
	
	public Integer getDataMemoryMin() {
		return dataMemoryMin;
	}
	
	public void setDataMemoryMin(String dataMemoryMin) {
		this.dataMemoryMin = Integer.valueOf(dataMemoryMin);
	}
	
	public String[] getBanCategories() {
		return banCategories;
	}
	
	public void setBanCategories(String banCategories) {
		this.banCategories = banCategories.split(",");
	}

}
