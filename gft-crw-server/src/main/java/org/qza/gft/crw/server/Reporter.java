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

	public Reporter(final Context context) {
		this.context = context;
		this.props = context.getProps();
		this.log = LoggerFactory.getLogger(Reporter.class);
	}

	@Override
	public void run() {
		context.end();
		String report = makeReport();
		log.info(new Date() + "\n" + report);
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
		Integer queueMax = props.getQueueMaxsize();
		Integer poolInit = props.getTpoolInitsize();
		Integer poolMax = props.getTpoolMaxsize();
		Integer visitedSize = context.visitedSize();
		Integer queueSize = context.queueSize();
		String completedTasks = StatsUtils.decimalFormat(context
				.completedTasksCount());
		Integer remainedTasks = context.activeTasksCount();
		String visitedInSecond = StatsUtils.formatPerSecond(visitedSize,
				duration);
		String report = String.format(template, queueMax, poolInit, poolMax,
				durationStr, completedTasks, remainedTasks, queueSize,
				visitedSize, visitedInSecond);
		return report;
	}

}
