package org.qza.gft.crw.server.spawn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.MessageConverter;
import org.qza.gft.crw.server.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class NioChannel {

	private State state;

	private Future<Integer> readFuture;

	private Future<Integer> writeFuture;

	private String message;

	final private Logger log;

	final private Context context;

	final private ByteBuffer readBuffer;

	final private MessageConverter converter;

	final private AsynchronousSocketChannel socket;
	
	static final int BUFFER_SIZE = 2048;

	public NioChannel(final Context context,
			final AsynchronousSocketChannel socket) {
		this.context = context;
		this.socket = socket;
		this.converter = new MessageConverter();
		this.log = LoggerFactory.getLogger(NioChannel.class);
		this.readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
	}

	public void shutdown() {
		// TODO
	}

	public synchronized void react() {
		if (state == State.READ) {
			doRead();
		} else {
			doWrite();
		}
	}

	private void doRead() {
		if (readFuture == null) {
			readMessage();
		} else {
			if (doneRead()) {
				process();
				cleanRead();
				state = State.WRITE;
			}
		}
	}

	private void doWrite() {
		if (writeFuture == null) {
			if(takeMessage()){
				writeMessage();
			}
		} else {
			if (doneWrite()) {
				cleanWrite();
				state = State.READ;
			}
		}
	}

	private void cleanRead() {
		this.readFuture = null;
		this.readBuffer.clear();
	}

	private void cleanWrite() {
		this.message = null;
		this.writeFuture = null;
	}

	private boolean takeMessage() {
		message = context.getQueue().poll();
		return message != null;
	}

	public void process() {
		try {
			readFuture.get();
			byte[] data = readBuffer.array();
			if (data != null && data.length > 0) {
				processData(data);
			} else {
				socket.close();
				state = State.DISCONNETED;
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			log.error(e.getMessage());
		}
	}

	public boolean doneRead() {
		return readFuture.isDone();
	}

	public boolean doneWrite() {
		return writeFuture.isDone();
	}

	private void readMessage() {
		if (socket.isOpen()) {
			readFuture = socket.read(readBuffer);
		}
	}

	private void processData(byte[] data) {
		try {
			Message message = converter.read(data);
			if (message != null) {
				context.addMessage(message);
			}
		} catch (RuntimeException ex) {
			log.error("Cannot convert message :  \n" + new String(data) + "\n");
		}
	}

	private void writeMessage() {
		if (socket.isOpen()) {
			writeFuture = socket.write(ByteBuffer.wrap(message.getBytes()));
		}
	}

	static enum State {
		READ, WRITE, DISCONNETED;
	}

}
