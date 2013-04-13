package org.qza.gft.crw.client.crawler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.client.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Client implements Runnable {

	final private Logger log;

	final private Crawler crawler;

	final private Connection connection;

	public Client(String name, final Context context,
			final ServerAddress address, final Crawler crawler) {
		this.crawler = crawler;
		this.connection = new Connection(context, address);
		this.log = LoggerFactory.getLogger(name);
	}

	@Override
	public void run() {
		if (connection.check()) {
			while (true) {
				try {
					String link = connection.readMessage();
					if(link!=null) {
						String[] results = crawler.crawlResults(link);
						connection.writeMessages(results);
					}
				} catch (TimeoutException e) {
					log.error("No server response");
				} catch (InterruptedException | ExecutionException e) {
					log.error("Connection interupted");
					break;
				} catch (Exception e) {
					log.error("Error:", e);
					break;
				}
			}
		}
	}

	public void shutdown() {
		connection.shutdown();
	}

}
