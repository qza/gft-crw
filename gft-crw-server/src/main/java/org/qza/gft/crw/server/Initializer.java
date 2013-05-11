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
		} else {
			initQueue();
		}
		log.info("Server state initialized");
	}
	
	public void initQueue() {
		log.info("Loading queue ... ");
		FileUtils.load(context.getProps().getDataFileQueue(),
				context.getQueue());
	}

	public void initFromDatabase() {
		try {
			System.gc();
			log.info("Loading collected ... ");
			Set<String> collectedBase = context.getStoreService().collected();
			System.gc();
			Thread.sleep(1000);
			log.info("Loading visited ... ");
			Set<String> visitedBase = context.getStoreService().visited(context.getProps().getDataInitSize());
			System.gc();
			Thread.sleep(1000);
			context.getCollected().addAll(collectedBase);
			log.info("Added collected... ");
			context.getVisited().addAll(visitedBase);
			context.getVisited().addAll(collectedBase);
			log.info("Added visited... ");
			visitedBase.removeAll(collectedBase);
			context.getQueue().addAll(visitedBase);
			log.info("Added queue... ");
			collectedBase.clear();
			visitedBase.clear();
			System.gc();
			Thread.sleep(1000);
			context.setInitialCollected(context.getCollected().size());
			context.setInitialVisited(context.getVisited().size());
			log.info("Server state initialized");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadDemoSetup() {
		initFromDatabase();
		context.getVisited().clear();
		context.getCollected().clear();
		log.warn("Demo setup");
	}

}
