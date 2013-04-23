package org.qza.gft.crw.store;

/**
 * @author gft
 */
public class Props {

	private Boolean logTime;
	private String dbHost;
	private Integer dbPort;
	private Integer maxClients;
	private String storeName;
	private String collectionProducts;

	public Boolean getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = Boolean.valueOf(logTime);
	}

	public String getDbHost() {
		return dbHost;
	}

	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	public Integer getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = Integer.valueOf(dbPort);
	}

	public Integer getMaxClients() {
		return maxClients;
	}

	public void setMaxClients(String maxClients) {
		this.maxClients = Integer.valueOf(maxClients);
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getCollectionProducts() {
		return collectionProducts;
	}

	public void setCollectionProducts(String collectionProducts) {
		this.collectionProducts = collectionProducts;
	}

}
