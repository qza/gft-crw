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

	final private Logger log;

	final private Context context;

	final private Integer duration;

	final private Integer perServer;

	final private List<Client> clients;

	final private ExecutorService executor;

	public Spawner(final Context context, final ExecutorService executor) {
		this.log = LoggerFactory.getLogger(Spawner.class);
		this.context = context;
		this.executor = executor;
		this.clients = new ArrayList<>();
		this.duration = context.getProps().getSpawnTime();
		this.perServer = context.getProps().getSpawnPerserver();
	}

	public void spawn() throws InterruptedException {
		context.start();
		initializeClients();
		work(duration, TimeUnit.MINUTES);
		terminateClients();
		terminateExecuton();
		context.end();
	}

	private void initializeClients() {
		Crawler crawler = new JsoupCrawler(context);
		Iterator<ServerAddress> it = context.getServerList().iterator();
		while (it.hasNext()) {
			connectToServer(it.next(), crawler);
		}
		log.info(String.format("%d clients initialized", clients.size()));
	} 

	private void connectToServer(ServerAddress address, Crawler crawler) {
		for (int i = 0; i < perServer; i++) {
			Client worker = new Client("C" + i, context, address, crawler);
			clients.add(worker);
			executor.execute(worker);
		}
	}

	private void work(long time, TimeUnit unit) {
		try {
			executor.awaitTermination(time, unit);
		} catch (InterruptedException e1) {
			log.warn("Spawner interupted");
		}
	}

	private void terminateClients() {
		for (Iterator<Client> iterator = clients.iterator(); iterator.hasNext();) {
			Client client = iterator.next();
			client.shutdown();
		}
		log.info("Clients terminated");
	}

	private void terminateExecuton() {
		try {
			executor.shutdown();
			executor.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			log.warn("Termination interupted");
		}
		log.info("Execution completed");
	}

}
