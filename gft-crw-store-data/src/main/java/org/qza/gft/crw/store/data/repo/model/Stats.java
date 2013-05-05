package org.qza.gft.crw.store.data.repo.model;

/**
 * @author gft
 */
public class Stats {

	private long recordCount;
	private long forGiftCount;

	public Stats() {
	}

	public long getRecordCount() {
		return recordCount;
	}

	public long getForGiftCount() {
		return forGiftCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public void setForGiftCount(long forGiftCount) {
		this.forGiftCount = forGiftCount;
	}

}
