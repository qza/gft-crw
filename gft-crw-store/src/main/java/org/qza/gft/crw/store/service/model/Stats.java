package org.qza.gft.crw.store.service.model;

/**
 * @author gft
 */
public class Stats {

	private long recordCount;
	private long visitedCount;
	private long forGiftCount;

	public Stats() {
	}

	public long getRecordCount() {
		return recordCount;
	}

	public long getVisitedCount() {
		return visitedCount;
	}

	public long getForGiftCount() {
		return forGiftCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public void setVisitedCount(long visitedCount) {
		this.visitedCount = visitedCount;
	}

	public void setForGiftCount(long forGiftCount) {
		this.forGiftCount = forGiftCount;
	}

}
