package org.qza.gft.crw.server.spawn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.qza.gft.crw.server.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class ServerWorker implements Runnable {

	final AsynchronousSocketChannel worker;

	final Context context;

	final Logger log = LoggerFactory.getLogger(ServerWorker.class);

	public ServerWorker(final Context context,
			final AsynchronousSocketChannel worker) {
		this.context = context;
		this.worker = worker;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String message = context.getQueue().take();
				if (writeMessage(worker, message)) {
					// log.info("Message sent");
				}
				if (readMessage(worker)) {
					// log.info("Message received");
				}
				log.info("Visited: " + context.visitedSize());
			} catch (InterruptedException | ExecutionException e) {
				log.warn("Client disconected");
				try {
					worker.close();
				} catch (IOException ioe) {
					log.warn("Can't close worker", ioe);
				}
			}
		}
	}

	private boolean readMessage(AsynchronousSocketChannel worker)
			throws InterruptedException, ExecutionException {
		boolean success = false;
		ByteBuffer readBuffer = ByteBuffer.allocate(4096);
		try {
			if (worker.isOpen()) {
				Future<Integer> readData = worker.read(readBuffer);
				readData.get(1, TimeUnit.MINUTES);
				String message = new String(readBuffer.array()).trim();
				message = message.replaceAll("\\[", "").replaceAll("\\]", "");
				String[] links = message.split(",");
				for (int i = 0; i < links.length; i++) {
					context.addLink(links[i].trim());
				}
				success = true;
			}
		} catch (TimeoutException e) {
			log.error("Client didn't respond in time");
		}
		return success;
	}

	private boolean writeMessage(AsynchronousSocketChannel worker,
			String message) throws InterruptedException, ExecutionException {
		boolean success = false;
		byte[] databytes = message.getBytes();
		ByteBuffer data = ByteBuffer.wrap(databytes);
		if (worker.isOpen()) {
			worker.write(data).get();
			success = true;
		}
		return success;
	}

}
