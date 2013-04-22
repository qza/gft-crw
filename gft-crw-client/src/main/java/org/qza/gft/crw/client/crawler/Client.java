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

	final private Logger log;

	final private Crawler crawler;

	final private Connection connection;

	final private MessageConverter converter;

	final private AtomicInteger errorCount;

	public Client(String name, final Context context,
			final ServerAddress address, final Crawler crawler) {
		this.crawler = crawler;
		this.connection = new Connection(context, address);
		this.log = LoggerFactory.getLogger(name);
		this.converter = new MessageConverter();
		this.errorCount = new AtomicInteger(0);
	}

	@Override
	public void run() {
		if (connection.check()) {
			work();
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
			} else {
				log.warn("No crawler message: " + link);
				checkExit();
			}
		} else {
			log.warn("Bad link : " + link);
			checkExit();
		}
	}

	private void checkExit() throws InterruptedException {
		if (errorCount.incrementAndGet() > 3) {
			log.error("Too many bad messages. Shuting down...");
			throw new InterruptedException();
		}
	}

}
