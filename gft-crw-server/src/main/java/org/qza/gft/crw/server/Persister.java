package org.qza.gft.crw.server;

import java.util.Date;
import java.util.List;

import org.qza.gft.crw.FileUtils;
import org.qza.gft.crw.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Persister {

	private final Context context;
	private final Props props;
	private final Logger log;

	private final String dataFileQueue;
	private final String dataFileVisited;
	private final String dataFileProducts;

	public Persister(final Context context) {
		this.context = context;
		this.props = context.getProps();
		this.dataFileQueue = props.getDataFileQueue();
		this.dataFileVisited = props.getDataFileVisited();
		this.dataFileProducts = props.getDataFileProducts();
		this.log = LoggerFactory.getLogger(Reporter.class);
	}

	public void persist() {
		log.info("Persisting product data");
		List<Message> messages = context.getProductDataClone();
		context.getProductData().clear();
		FileUtils.writeMessagesGzip(dataFileProducts, messages);
		messages.clear();
		log.info("Persisting queue");
		List<String> queue = context.getQueueClone();
		context.getQueue().clear();
		FileUtils.writeTextGzip(dataFileQueue, queue);
		queue.clear();
		log.info("Persisting visited links");
		List<String> visited = context.getVisitedClone();
		context.getVisited().clear();
		FileUtils.writeTextGzip(dataFileVisited, visited);
		visited.clear();
		log.info("Server state persisted. " + new Date());
	}

}