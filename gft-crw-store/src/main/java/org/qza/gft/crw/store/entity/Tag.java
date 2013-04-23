package org.qza.gft.crw.store.entity;

/**
 * @author gft
 */
public enum Tag {
	GIFT("gift"), NOT_GIFT("not-gift");

	private String value;

	Tag(String value) {
		this.value = value;
	}

	public String val() {
		return value;
	}
}
