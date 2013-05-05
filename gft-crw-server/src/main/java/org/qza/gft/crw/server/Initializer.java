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
		if(context.getStoreService().stats().getRecordCount() > 0) {
			initFromDatabase();
		} else {
			initFromFiles();
		}
		log.info("Server state initialized");
	}

	private void initFromDatabase() {
		System.gc();
		Set<String> collectedBase = context.getStoreService().collected();
		log.info("Collected loaded");
		System.gc();
		Set<String> visitedBase = context.getStoreService().visited();
		log.info("Visited loaded");
		System.gc();
		context.getCollected().addAll(collectedBase);
		context.getVisited().addAll(visitedBase);
		context.getVisited().addAll(collectedBase);
		visitedBase.removeAll(collectedBase);
		System.gc();
		Iterator<String> it = visitedBase.iterator();
		while (it.hasNext() && context.getQueue().size() <= 100) {
			context.getQueue().add(it.next());
		}
		collectedBase.clear();
		visitedBase.clear();
		System.gc();
		log.info("Server state initialized");
	}
	
	
	private void initFromFiles(){
		FileUtils.load(context.getProps().getDataFileQueue(), context.getQueue());
	}

}
