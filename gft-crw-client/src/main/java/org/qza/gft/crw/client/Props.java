package org.qza.gft.crw.client;

import java.util.List;

import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.ServerAddresses;

/**
 * @author gft
 */
public class Props {

	private Boolean logTime;

	private Boolean logCompleted;

	private Integer parserTimeout;

	private Integer parserMaxbytes;

	private Integer spawnPerserver;

	private Integer spawnTime;

	private Integer tpoolInitsize;

	private Integer tpoolMaxsize;

	private Integer clientTimeout;

	private Integer clientDuration;

	private ServerAddresses servers;

	private String parserCssName;

	private String parserCssCategory;

	private String parserCssPrice;

	private String parserCssRating;

	private String parserCssRelated;

	public Boolean getLogTime() {
		return logTime;
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

	public Integer getParserTimeout() {
		return parserTimeout;
	}

	public void setParserTimeout(String parserTimeout) {
		this.parserTimeout = Integer.valueOf(parserTimeout);
	}

	public Integer getParserMaxbytes() {
		return parserMaxbytes;
	}

	public void setParserMaxbytes(String parserMaxbytes) {
		this.parserMaxbytes = Integer.valueOf(parserMaxbytes);
	}

	public List<ServerAddress> getServerList() {
		return servers.getList();
	}

	public void setServerList(String serverList) {
		servers = new ServerAddresses(serverList);
	}

	public Integer getSpawnPerserver() {
		return spawnPerserver;
	}

	public void setSpawnPerserver(String spawnPerserver) {
		this.spawnPerserver = Integer.valueOf(spawnPerserver);
	}

	public Integer getSpawnTime() {
		return spawnTime;
	}

	public void setSpawnTime(String spawnTime) {
		this.spawnTime = Integer.valueOf(spawnTime);
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

	public Integer getClientTimeout() {
		return clientTimeout;
	}

	public void setClientTimeout(String clientTimeout) {
		this.clientTimeout = Integer.valueOf(clientTimeout);
	}

	public Integer getClientDuration() {
		return clientDuration;
	}

	public void setClientDuration(String clientDuration) {
		this.clientDuration = Integer.valueOf(clientDuration);
	}

	public String getParserCssName() {
		return parserCssName;
	}

	public String getParserCssCategory() {
		return parserCssCategory;
	}

	public String getParserCssPrice() {
		return parserCssPrice;
	}

	public String getParserCssRating() {
		return parserCssRating;
	}

	public String getParserCssRelated() {
		return parserCssRelated;
	}

	public void setParserCssName(String parserCssName) {
		this.parserCssName = parserCssName;
	}

	public void setParserCssCategory(String parserCssCategory) {
		this.parserCssCategory = parserCssCategory;
	}

	public void setParserCssPrice(String parserCssPrice) {
		this.parserCssPrice = parserCssPrice;
	}

	public void setParserCssRating(String parserCssRating) {
		this.parserCssRating = parserCssRating;
	}

	public void setParserCssRelated(String parserCssRelated) {
		this.parserCssRelated = parserCssRelated;
	}

}
