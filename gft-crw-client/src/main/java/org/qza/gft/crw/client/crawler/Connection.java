package org.qza.gft.crw.client.crawler;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ReadPendingException;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.client.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Connection {

	private Logger log;

	private ServerAddress address;

	private Future<Void> connectFuture;

	private AsynchronousSocketChannel socket;

	public Connection(final Context context, final ServerAddress address) {
		this.log = LoggerFactory.getLogger(Connection.class);
		this.address = address;
		connect();
	}

	public void reconnect() {
		shutdown();
		connect();
		check();
	}

	private void connect() {
		try {
			socket = AsynchronousSocketChannel.open();
			connectFuture = socket.connect(new InetSocketAddress(address
					.getHost(), address.getPort()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean check() {
		boolean connected = false;
		if (waitForConnection()) {
			log.info(String.format("Connected to -> %s : %d",
					address.getHost(), address.getPort()));
			connected = true;
		} else {
			log.error(String.format("Not connected to -> %s : %d",
					address.getHost(), address.getPort()));
			connectFuture.cancel(true);
		}
		return connected;
	}

	private boolean waitForConnection() {
		boolean connected = false;
		for (int i = 0; i < 5; i++) {
			if (!connectFuture.isDone()) {
				log.warn("Waiting for server");
				zzz(1000);
			} else {
				connected = true;
			}
		}
		return connected;
	}

	public void shutdown() {
		try {
			socket.close();
		} catch (IOException e) {
			log.error("Error:", e.getMessage());
		}
	}

	public String readMessage() throws InterruptedException,
			ExecutionException, TimeoutException {
		String message = getText(readData(256));
		return message;
	}

	public void writeMessage(byte[] message) throws InterruptedException,
			ExecutionException {
		ByteBuffer data = ByteBuffer.wrap(message);
		writeData(data);
	}

	public void writeMessage(String message) throws InterruptedException,
			ExecutionException {
		ByteBuffer data = ByteBuffer.wrap(message.getBytes());
		writeData(data);
	}

	public void writeMessages(String[] messages) throws InterruptedException,
			ExecutionException {
		ByteBuffer data = prepareResults(messages);
		writeData(data);
	}

	private void writeData(ByteBuffer data) throws InterruptedException,
			ExecutionException {
		socket.write(data).get();
	}

	private ByteBuffer readData(int size) throws InterruptedException,
			ExecutionException, TimeoutException, ReadPendingException {
		ByteBuffer readBuffer = ByteBuffer.allocate(size);
		socket.read(readBuffer).get();
		return readBuffer;
	}

	private ByteBuffer prepareResults(String[] results) {
		String resultString = Arrays.toString(results);
		byte[] resultStringBytes = resultString.getBytes();
		return ByteBuffer.wrap(resultStringBytes);
	}

	private String getText(ByteBuffer buffer) {
		return new String(buffer.array()).trim();
	}

	private void zzz(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.interrupted();
		}
	}

}
