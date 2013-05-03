package org.qza.gft.crw.server;

import java.util.Iterator;
import java.util.Set;

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
		Set<String> collected = context.getStoreService().collected();
		context.getCollected().addAll(collected);
		Set<String> visited = context.getStoreService().visited();
		for (Iterator<String> iterator = visited.iterator(); iterator.hasNext();) {
			String string = iterator.next();
			context.getVisited().add(string);
			if(!collected.contains(string)) {
				context.getQueue().add(string);
			}
		}
		log.info("Server state initialized");
	}
	
//	public void initServerState() {
//		log.info("Loading queue");
//		FileUtils.load(queueFile, context.getQueue());
//		log.info("Loading visited links");
//		FileUtils.load(visitedFile, context.getVisited());
//		log.info("Server state initialized");
//	}

}
