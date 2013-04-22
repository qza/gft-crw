package org.qza.gft.crw.client.crawler;

import java.nio.channels.ReadPendingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.MessageConverter;
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

	final private MessageConverter converter;

	public Client(String name, final Context context,
			final ServerAddress address, final Crawler crawler) {
		this.crawler = crawler;
		this.connection = new Connection(context, address);
		this.log = LoggerFactory.getLogger(name);
		this.converter = new MessageConverter();
	}

	@Override
	public void run() {
		if (connection.check()) {
			while (true) {
				try {
					String link = connection.readMessage();
					if (link != null && !link.equals("null")) {
						Message message = crawler.crawlResults(link);
						if (message != null) {
							byte[] messageData = converter.write(message);
							connection.writeMessage(messageData);
						} else {
							log.warn("No crawler message: " + link);
						}
					} else {
						log.warn("Bad link : " + link);
					}
				} catch (ReadPendingException e) {
					log.warn("Pending read!");
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
