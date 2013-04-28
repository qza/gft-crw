package org.qza.gft.crw.server.spawn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.MessageConverter;
import org.qza.gft.crw.server.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class ServerWorker implements Runnable {

	final private Logger log;

	final private Context context;

	final private MessageConverter converter;

	final private AsynchronousSocketChannel socket;

	public ServerWorker(final Context context,
			final List<ServerWorker> workers,
			final AsynchronousSocketChannel socket) {
		this.context = context;
		this.socket = socket;
		this.converter = new MessageConverter();
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
		ByteBuffer readBuffer = ByteBuffer.allocate(4096);
		if (socket.isOpen()) {
			try {
				socket.read(readBuffer).get();
			} catch (InterruptedException | ExecutionException e) {
				readBuffer = null;
				throw new RuntimeException("Interupted");
			}
			byte[] data = readBuffer.array();
			if (data != null && data.length > 0) {
				processData(data);
				success = true;
			}
		}
		return success;
	}

	private void processData(byte[] data) {
		Message message = converter.read(data);
		if (message != null) {
			context.addMessage(message);
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
		} catch (IOException ioe) {
			log.warn("Can't close worker", ioe);
		}
	}

}
