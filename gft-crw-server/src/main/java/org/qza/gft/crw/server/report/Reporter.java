package org.qza.gft.crw.server.report;

import org.qza.gft.crw.StatsUtils;
import org.qza.gft.crw.server.Context;
import org.qza.gft.crw.server.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Reporter implements Runnable {

	private final Context context;
	private final Props props;
	private final Logger log;

	public Reporter(final Context context) {
		this.context = context;
		this.props = context.getProps();
		this.log = LoggerFactory.getLogger(Reporter.class);
	}

	@Override
	public void run() {
		try {
			context.end();
			String report = makeReport();
			log.info("\n" + report);
		} catch (Throwable th) {
			log.error("Error creating report :: " + th.getMessage());
		}
	}

	public String makeReport() {
		long duration = context.getDurationInNanos();
		long completed = context.completedTasksCount();
		int queueMax = props.getQueueMaxsize();
		int poolInit = props.getTpoolInitsize();
		int poolMax = props.getTpoolMaxsize();
		int qSize = context.queueSize();
		int vSize = context.visitedSize();
		int cSize = context.collectedSize();
		int pSize = context.productsSize();
		int remainedTasks = context.activeTasksCount();
		int freeMemory = (int) Runtime.getRuntime().freeMemory()
				/ (1024 * 1024);
		String completedTasks = StatsUtils.decimalFormat(completed);
		String durationStr = StatsUtils.formatDuration(duration);
		String visitedInSecond = StatsUtils.formatPerSecond(vSize, duration);
		String collectedInSecond = StatsUtils.formatPerSecond(cSize, duration);
		String template = new Builder(new Template()).build();
		String report = String.format(template, queueMax, poolInit, poolMax,
				durationStr, completedTasks, remainedTasks, qSize, vSize,
				cSize, pSize, visitedInSecond, collectedInSecond, freeMemory);
		return report;
	}

}
