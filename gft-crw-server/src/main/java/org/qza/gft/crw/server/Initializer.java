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

	final private Context context;

	final private Logger log;

	public Initializer(final Context context) {
		this.context = context;
		this.log = LoggerFactory.getLogger(Initializer.class);
	}
	
	public void initServerState(){
		FileUtils.load(context.getProps().getDataFileQueue(), context.getQueue());
		log.info("Server state initialized");
	}

//	public void initServerState() {
//		Set<String> collectedBase = context.getStoreService().collected();
//		log.info("Collected loaded");
//		Set<String> visitedBase = context.getStoreService().visited();
//		log.info("Visited loaded");
//		Set<String> collected = context.getCollected();
//		Set<String> visited = context.getVisited();
//		collected.addAll(collectedBase);
//		visited.addAll(visitedBase);
//		visited.addAll(collectedBase);
//		visitedBase.removeAll(collectedBase);
//		Iterator<String> it = visitedBase.iterator();
//		while (it.hasNext() && context.getQueue().size() <= 100) {
//			context.getQueue().add(it.next());
//		}
//		collectedBase.clear();
//		visitedBase.clear();
//		System.gc();
//		FileUtils.load(context.getProps().getDataFileQueue(), context.getQueue());
//		log.info("Server state initialized");
//	}

}
