package org.qza.gft.crw.server.spawn;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.MessageConverter;
import org.qza.gft.crw.server.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public NioChannel(final Context context,
			final AsynchronousSocketChannel socket) {
		this.context = context;
		this.socket = socket;
		this.converter = new MessageConverter();
		this.log = LoggerFactory.getLogger(NioChannel.class);
		this.readBuffer = ByteBuffer.allocate(4096);
	}

	public synchronized void react() {
		if(state == State.READ) {
			if(readFuture == null) {
//				log.info("Reading message");
				readMessage();
			} 
			else {
				if(doneRead()) {
//					log.info("Done reading");
					process();
					cleanRead();
					state = State.WRITE;
//					log.info("Reading complete");
				}
			}
		} else {
			if(writeFuture == null) {
//				log.info("Writing message");
				takeMessage();
				writeMessage();
			} else {
				if(doneWrite()) {
//					log.info("Done writing");
					cleanWrite();
					state = State.READ;
				}
			}
		}
	}
	
	public void shutdown() {
		
	}
	
	private void cleanRead() {
		this.readFuture = null;
		this.readBuffer.clear();
	}
	
	private void cleanWrite() {
		this.message = null;
		this.writeFuture = null;
	}
	
	private void takeMessage(){
		try {
			message = context.getQueue().take();
		} catch (InterruptedException e) {
			log.warn("Can't read queue");
		}
	}

	public void process() {
		try {
			readFuture.get();
			byte[] data = readBuffer.array();
			processData(data);
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage());
		}
	}
	
	public boolean doneRead(){
		return readFuture.isDone();
	}
	
	public boolean doneWrite(){
		return writeFuture.isDone();
	}
	
	private void readMessage() {
		if (socket.isOpen()) {
			readFuture = socket.read(readBuffer);
		}
	}

	private void processData(byte[] data) {
		Message message = converter.read(data);
		if (message != null) {
			context.addMessage(message);
		}
	}

	private void writeMessage() {
		byte[] databytes = message.getBytes();
		ByteBuffer data = ByteBuffer.wrap(databytes);
		if (socket.isOpen()) {
			writeFuture = socket.write(data);
		}
	}
	
	static enum State {
		READ, WRITE;
	}
	
}
