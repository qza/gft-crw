package org.qza.gft.crw.server;

import java.util.Set;

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
		Set<String> collectedBase = context.getStoreService().collected();
		Set<String> visitedBase = context.getStoreService().visited();
		Set<String> collected = context.getCollected();
		Set<String> visited = context.getVisited();
		collected.addAll(collectedBase);
		visited.addAll(visitedBase);
		visited.addAll(collectedBase);
		visitedBase.removeAll(collectedBase);
		context.getQueue().addAll(visitedBase);
		collectedBase.clear();
		visitedBase.clear();
		System.gc();
		log.info("Server state initialized");
	}

}
