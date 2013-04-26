package org.qza.gft.crw.server;

import org.qza.gft.crw.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Initializer {

	final private Props props;
	final private Context context;

	final private String queueFile;
	final private String visitedFile;

	final private Logger log;

	public Initializer(final Context context) {
		this.context = context;
		this.props = context.getProps();
		this.queueFile = props.getDataFileQueue();
		this.visitedFile = props.getDataFileVisited();
		this.log = LoggerFactory.getLogger(Initializer.class);
	}

	public void initServerState() {
		log.info("Loading queue");
		FileUtils.load(queueFile, context.getQueue());
		log.info("Loading visited links");
		FileUtils.load(visitedFile, context.getVisited());
		log.info("Server state initialized");
	}

}
