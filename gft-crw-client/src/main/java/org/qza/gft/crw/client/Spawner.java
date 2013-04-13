package org.qza.gft.crw.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.client.crawler.Client;
import org.qza.gft.crw.client.crawler.Crawler;
import org.qza.gft.crw.client.crawler.impl.JsoupCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Spawner {

	final private Context context;

	final private ExecutorService executor;

	final private Logger log;

	final private List<Client> clients;

	final private Integer duration;

	public Spawner(final Context context, final ExecutorService executor) {
		this.log = LoggerFactory.getLogger(Spawner.class);
		this.context = context;
		this.executor = executor;
		this.clients = new ArrayList<>();
		this.duration = context.getProps().getClientDuration();
	}

	public void spawn() throws InterruptedException {
		context.start();
		initializeClients();
		wait(duration, TimeUnit.MINUTES);
		terminateClients();
		context.end();
	}

	private void initializeClients() {
		Integer spawnPerServer = context.getProps().getSpawnPerserver();
		Crawler crawler = new JsoupCrawler(context);
		for (Iterator<ServerAddress> it = context.getServerList().iterator(); it
				.hasNext();) {
			ServerAddress address = it.next();
			for (int i = 0; i < spawnPerServer; i++) {
				Client worker = new Client("C" + i, context, address, crawler);
				clients.add(worker);
				executor.execute(worker);
			}
		}
		log.info(String.format("%d clients initialized", clients.size()));
	}

	private void wait(long time, TimeUnit unit) {
		try {
			executor.awaitTermination(time, unit);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private void terminateClients() {
		for (Iterator<Client> iterator = clients.iterator(); iterator.hasNext();) {
			Client client = iterator.next();
			client.shutdown();
		}
		try {
			executor.shutdown();
			executor.awaitTermination(1, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		log.info("Clients terminated");
	}

}
