package org.qza.gft.crw.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.server.spawn.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Spawner {

	final private Logger log;

	final private Context context;

	final private Map<ServerAddress, Server> serverMap;

	final private Integer duration;

	public Spawner(final Context context) {
		this.log = LoggerFactory.getLogger(Spawner.class);
		this.context = context;
		this.serverMap = new HashMap<>(context.getServerList().size());
		this.duration = context.getProps().getServerDuration();
	}

	public void spawn() {
		context.start();
		initializeServers();
		initializeReporter();
		initializePersister();
		work(duration, TimeUnit.MINUTES);
		terminateServers();
		context.end();
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

	private void initializeReporter() {
		int interval = context.getProps().getReportLogInterval();
		if (interval > 0) {
			Reporter reporter = new Reporter(context);
			context.getScheduler().scheduleWithFixedDelay(reporter, 1,
					interval, TimeUnit.SECONDS);
			log.info("Reporter scheduled");
		}
	}

	private void initializePersister() {
		Persister persister = new Persister(context);
		context.getScheduler().scheduleWithFixedDelay(persister, 0, 60,
				TimeUnit.SECONDS);
		log.info("Persister scheduled");
	}

	private void work(long time, TimeUnit unit) {
		try {
			executor().awaitTermination(time, unit);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private void terminateServers() {
		try {
			Iterator<Server> servers = serverMap.values().iterator();
			while (servers.hasNext()) {
				servers.next().shutdown();
			}
			executor().shutdown();
			executor().awaitTermination(1, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		log.info(String.format("Servers terminated. Active %d",
				serverMap.size()));
	}

	private ExecutorService executor() {
		return context.getExecutor();
	}

}
