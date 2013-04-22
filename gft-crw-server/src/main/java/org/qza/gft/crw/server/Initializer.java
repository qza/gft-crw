package org.qza.gft.crw.server;

import org.qza.gft.crw.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Initializer {

	final private Props props;
	final private Context context;

	final private Logger log;

	public Initializer(final Context context) {
		this.context = context;
		this.props = context.getProps();
		log = LoggerFactory.getLogger(Initializer.class);
	}

	public void initialize() {
		log.info("Loading queue");
		FileUtils.loadData(props.getDataFileQueue(), context.getQueue());
		log.info("Loading visited links");
		FileUtils.loadData(props.getDataFileVisited(), context.getVisited());
	}

}
