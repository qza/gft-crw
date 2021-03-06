package org.qza.gft.crw.client.crawler;

import java.nio.channels.ReadPendingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

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

	private Logger log;

	private Crawler crawler;

	private Connection connection;

	private MessageConverter converter;

	private AtomicInteger badLinks;

	public Client(String name, final Context context,
			final ServerAddress address, final Crawler crawler) {
		this.crawler = crawler;
		this.connection = new Connection(context, address);
		this.log = LoggerFactory.getLogger(name);
		this.converter = new MessageConverter();
		this.badLinks = new AtomicInteger();
	}

	@Override
	public void run() {
		if (connection.check()) {
			work();
			shutdown();
		}
	}

	public void shutdown() {
		connection.shutdown();
	}

	private void work() {
		while (true) {
			try {
				String link = connection.readMessage();
				processLink(link);
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

	private void processLink(String link) throws InterruptedException,
			ExecutionException {
		if (link != null && !link.equals("") && !link.equals("null")) {
			Message message = crawler.crawlResults(link);
			if (message != null) {
				byte[] messageData = converter.write(message);
				connection.writeMessage(messageData);
			}
		} else {
			int blcount = badLinks.incrementAndGet();
			log.warn(String.format("Bad link # %s :::  %s", blcount, link));
			if (blcount > 3) {
				log.error("Too many bad links, shutting down.");
				throw new InterruptedException();
			}
		}
	}

}
