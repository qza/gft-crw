package org.qza.gft.crw.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.MessageConverter;
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
		List<Message> messages = this.context.getProductDataClone();
		persistData(dataFileProducts, messages);
		persistStringCollection(dataFileQueue, this.context.getQueueClone());
		persistStringCollection(dataFileVisited, this.context.getVisitedClone());
		log.info("All data persisted. " + new Date());
	}
	
	public void persistStringCollection(String fileName, Collection<String> data) {
		FileWriter writer;
		try {
			File file = new File(fileName);
			writer = new FileWriter(file);
			for (Iterator<String> iterator = data.iterator(); iterator
					.hasNext();) {
				writer.write(iterator.next());
				writer.write("\r\n");
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void persistData(String fileName, Collection<Message> data) {
		FileWriter writer;
		try {
			File file = new File(fileName);
			writer = new FileWriter(file);
			for (Iterator<Message> iterator = data.iterator(); iterator
					.hasNext();) {
				Message message = iterator.next();
				String text = new String(new MessageConverter().write(message));
				writer.write(text);
				writer.write("\r\n");
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
