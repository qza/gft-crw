package org.qza.gft.crw.server.spawn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
			String message = null;
			try {
				message = context.getQueue().take();
			} catch (InterruptedException e) {
				log.warn("Can't read queue");
			}
			try {
				writeMessage(socket, message);
				readMessage(socket);
			} catch (RuntimeException e) {
				log.warn("Client disconected");
				shutdown();
				break;
			}
		}
	}

	private boolean readMessage(AsynchronousSocketChannel socket) {
		boolean success = false;
		ByteBuffer readBuffer = ByteBuffer.allocate(2048);
		if (socket.isOpen()) {
			try {
				socket.read(readBuffer).get();
			} catch (InterruptedException | ExecutionException e) {
				readBuffer = null;
				throw new RuntimeException("Interupted");
			}
			String message = new String(readBuffer.array()).trim();
			if (message != null && !message.equals("null")) {
				processMessage(message);
				success = true;
			}
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
			String message) {
		boolean success = false;
		byte[] databytes = message.getBytes();
		ByteBuffer data = ByteBuffer.wrap(databytes);
		if (worker.isOpen()) {
			try {
				worker.write(data).get();
				success = true;
			} catch (InterruptedException | ExecutionException e) {
				data = null;
				throw new RuntimeException("Interupted");
			}
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
