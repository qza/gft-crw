package org.qza.gft.crw.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.server.report.Reporter;
import org.qza.gft.crw.server.report.WebSocketServer;
import org.qza.gft.crw.server.spawn.Server;
import org.qza.gft.crw.server.store.DbPersister;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Spawner {

	final private Logger log;

	final private Context context;

	final private Initializer initializer;

	final private Integer duration;

	final private Map<ServerAddress, Server> serverMap;

	public Spawner(final Context context, final Initializer initializer) {
		this.log = LoggerFactory.getLogger(Spawner.class);
		this.context = context;
		this.initializer = initializer;
		this.serverMap = new HashMap<>(context.getServerList().size());
		this.duration = context.getProps().getServerDuration();
	}

	public void spawn() {
		context.start();
		initializeState();
		initializeServers();
		initializeReporter();
		initializeDbPersister();
		work(duration, TimeUnit.MINUTES);
		terminateServers();
		terminateScheduler();
		persistData();
		context.end();
	}

	private void initializeState() {
		initializer.initServerState();
	}

	private void initializeServers() {
		for (Iterator<ServerAddress> iterator = context.getServerList()
				.iterator(); iterator.hasNext();) {
			ServerAddress serverAddress = iterator.next();
			Server server = new Server(context, serverAddress);
			serverMap.put(serverAddress, server);
			context.execute(server);
		}
		log.info(String.format("%d servers initialized", serverMap.size()));
	}

	private void initializeDbPersister() {
		DbPersister dbPersister = new DbPersister(context);
		scheduler().scheduleWithFixedDelay(dbPersister, 5,
				context.getProps().getDataPersistInterval(), TimeUnit.SECONDS);
		log.info("Db Persister scheduled");
	}

	private void initializeReporter() {
		int interval = context.getProps().getReportLogInterval();
		if (interval > 0) {
			Reporter reporter = new Reporter(context);
			scheduler().scheduleWithFixedDelay(reporter, 0, interval,
					TimeUnit.SECONDS);
			log.info("Reporter scheduled");
//			try {
//				context.execute(new WebSocketServer(context, reporter));
//			} catch (Exception e) {
//				log.error("Error starting socket server", e);
//			}
		}
	}

	private void persistData() {
		Persister persister = new Persister(context);
		persister.persist();
	}

	private void work(long time, TimeUnit unit) {
		try {
			executor().awaitTermination(time, unit);
		} catch (InterruptedException e1) {
			log.error("Spawner interupted during work");
		}
	}

	private void terminateServers() {
		try {
			Iterator<Server> servers = serverMap.values().iterator();
			while (servers.hasNext()) {
				servers.next().shutdown();
			}
			executor().shutdownNow();
			executor().awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			log.error("Server termination interupted");
		}
		log.info(String.format("Servers terminated. Active %d",
				serverMap.size()));
	}

	private void terminateScheduler() {
		try {
			scheduler().shutdownNow();
			scheduler().awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			log.error("Scheduler termination interupted");
		}
		log.info("Scheduler terminated");
	}

	private ExecutorService executor() {
		return context.getExecutor();
	}

	private ScheduledExecutorService scheduler() {
		return context.getScheduler();
	}

}
