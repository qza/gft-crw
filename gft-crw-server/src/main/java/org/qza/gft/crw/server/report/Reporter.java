package org.qza.gft.crw.server.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

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
			log.info(new Date() + "\n" + report);
		} catch (Exception ex) {
			log.error("Error creating report", ex);
		} catch (Throwable th) {
			log.error("Error creating report", th);
		}
	}

	public void writeReport() {
		FileWriter writer;
		try {
			File file = new File(props.getReportFilename());
			writer = new FileWriter(file);
			String report = makeReport();
			writer.write(report);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String makeReport() {
		String template = new Builder(new Template()).build();
		long duration = context.getDurationInNanos();
		String durationStr = StatsUtils.formatDuration(duration);
		Long completed = context.completedTasksCount();
		Integer queueMax = props.getQueueMaxsize();
		Integer poolInit = props.getTpoolInitsize();
		Integer poolMax = props.getTpoolMaxsize();
		Integer qSize = context.queueSize();
		Integer vSize = context.visitedSize();
		Integer cSize = context.collectedSize();
		Integer pSize = context.productsSize();
		String completedTasks = StatsUtils.decimalFormat(completed);
		Integer remainedTasks = context.activeTasksCount();
		Integer freeMemory = (int) Runtime.getRuntime().freeMemory() / (1024 * 1024);
		String visitedInSecond = StatsUtils.formatPerSecond(vSize, duration);
		String collectedInSecond = StatsUtils.formatPerSecond(cSize, duration);
		String report = String.format(template, queueMax, poolInit, poolMax,
				durationStr, completedTasks, remainedTasks, qSize, vSize,
				cSize, pSize, visitedInSecond, collectedInSecond, freeMemory);
		return report;
	}

}
