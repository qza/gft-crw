package org.qza.gft.crw.server;

import java.util.Set;

import org.qza.gft.crw.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Initializer {

	final private Context context;

	final private Logger log;

	public Initializer(final Context context) {
		this.context = context;
		this.log = LoggerFactory.getLogger(Initializer.class);
	}

	public void initServerState() {
		if (context.getStoreService().stats().getRecordCount() > 0) {
			initFromDatabase();
		}
		initQueue();
		log.info("Server state initialized");
	}
	
	public void initQueue() {
		log.info("Loading queue ... ");
		FileUtils.load(context.getProps().getDataFileQueue(),
				context.getQueue());
	}

	public void initFromDatabase() {
		System.gc();
		log.info("Loading collected ... ");
		Set<String> collectedBase = context.getStoreService().collected();
		System.gc();
		log.info("Loading visited ... ");
		Set<String> visitedBase = context.getStoreService().visited(500000);
		System.gc();
		context.getCollected().addAll(collectedBase);
		context.getVisited().addAll(visitedBase);
		context.getVisited().addAll(collectedBase);
		collectedBase.clear();
		visitedBase.clear();
		System.gc();
		log.info("Server state initialized");
	}
	
	public void loadCollected() {
		
	}
	
	public void loadVisited() {
		
	}

}
