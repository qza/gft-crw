package org.qza.gft.crw.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.qza.gft.crw.StatsUtils;
import org.qza.gft.crw.server.report.Builder;
import org.qza.gft.crw.server.report.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Reporter implements Runnable {

	private final Context context;
	private final Props props;
	private final Logger log;

	final private Integer memoryMin;
	final private Integer mb = 1024 * 1024;

	public Reporter(final Context context) {
		this.context = context;
		this.props = context.getProps();
		this.log = LoggerFactory.getLogger(Reporter.class);
		this.memoryMin = props.getDataMemoryMin();
	}

	@Override
	public void run() {
		try {
			context.end();
			String report = makeReport();
			log.info(new Date() + "\n" + report);
		} catch (Exception ex) {
			log.error("Error creating report", ex);
		} finally {
			checkMemory();
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

	private String makeReport() {
		String template = new Builder(new Template()).build();
		long duration = context.getDurationInNanos();
		String durationStr = StatsUtils.formatDuration(duration);
		Long completed = context.completedTasksCount();
		Integer queueMax = props.getQueueMaxsize();
		Integer poolInit = props.getTpoolInitsize();
		Integer poolMax = props.getTpoolMaxsize();
		Integer qSize = context.queueSize();
		Integer vSize = context.visitedSize();
		Integer pSize = context.getProductsSize();
		String completedTasks = StatsUtils.decimalFormat(completed);
		Integer remainedTasks = context.activeTasksCount();
		Integer freeMemory = (int) Runtime.getRuntime().freeMemory() / mb;
		String visitedInSecond = StatsUtils.formatPerSecond(vSize, duration);
		String report = String.format(template, queueMax, poolInit, poolMax,
				durationStr, completedTasks, remainedTasks, qSize, vSize,
				pSize, visitedInSecond, freeMemory);
		return report;
	}

	private void checkMemory() {
		long free = Runtime.getRuntime().freeMemory() / mb;
		if (free < memoryMin / 2) {
			log.warn("\n Running out of memory!!!\n");
		}
		if (free < memoryMin) {
			System.gc();
		}
	}

}
