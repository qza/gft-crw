package org.qza.gft.crw.server.spawn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.List;
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

	final private Logger log;

	final private Context context;

	final private List<ServerWorker> workers;

	final private AsynchronousSocketChannel socket;

	public ServerWorker(final Context context,
			final List<ServerWorker> workers,
			final AsynchronousSocketChannel socket) {
		this.context = context;
		this.socket = socket;
		this.workers = workers;
		this.log = LoggerFactory.getLogger(ServerWorker.class);
	}

	@Override
	public void run() {
		while (true) {
			try {
				String message = context.getQueue().take();
				if (writeMessage(socket, message)) {
					readMessage(socket);
				}
			} catch (InterruptedException | ExecutionException e) {
				log.warn("Client disconected");
				shutdown();
				break;
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
				readData.get(context.getProps().getServerTimeout(),
						TimeUnit.SECONDS);
				String message = new String(readBuffer.array()).trim();
				processMessage(message);
				success = true;
			}
		} catch (TimeoutException e) {
			log.warn("Client didn't respond in time");
		}
		return success;
	}

	private void processMessage(String message) {
		message = message.replaceAll("\\[", "").replaceAll("\\]", "");
		String[] links = message.split(",");
		for (int i = 0; i < links.length; i++) {
			context.addLink(links[i].trim());
		}
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

	public void shutdown() {
		try {
			socket.close();
			workers.remove(this);
		} catch (IOException ioe) {
			log.warn("Can't close worker", ioe);
		}
	}

}
