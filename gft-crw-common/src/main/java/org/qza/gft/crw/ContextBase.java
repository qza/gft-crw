package org.qza.gft.crw;

public class ContextBase {

	private long startNanos;

	private long endNanos;

	public void start() {
		setStartNanos(System.nanoTime());
	}

	public void end() {
		setEndNanos(System.nanoTime());
	}

	public long getStartNanos() {
		return startNanos;
	}

	public long getEndNanos() {
		return endNanos;
	}

	public void setStartNanos(long startNanos) {
		this.startNanos = startNanos;
	}

	public void setEndNanos(long endNanos) {
		this.endNanos = endNanos;
	}

	public long getDurationInNanos() {
		return endNanos - startNanos;
	}

	public long getDurationInMilis() {
		return getDurationInNanos() / 1000000;
	}

}
