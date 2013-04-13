package org.qza.gft.crw.server.spawn;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.qza.gft.crw.address.ServerAddress;
import org.qza.gft.crw.server.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Server implements Runnable {

	final Context context;

	final AsynchronousServerSocketChannel server;

	final Logger log = LoggerFactory.getLogger(Server.class);

	public Server(final Context context, final ServerAddress address) {
		this.context = context;
		try {
			SocketAddress sAddress = new InetSocketAddress(address.getHost(),
					address.getPort());
			server = AsynchronousServerSocketChannel.open().bind(sAddress);
			log.info(String.format("Started @ %s : %d", address.getHost(),
					address.getPort()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		try {
			startup();
		} finally {
			shutdown();
		}
	}

	private void startup() {
		try {
			while (true) {
				Future<AsynchronousSocketChannel> future = server.accept();
				AsynchronousSocketChannel worker = future.get();
				log.info("Connection established");
				initWorker(worker);
			}
		} catch (InterruptedException | ExecutionException e) {
			log.warn("Unexpected server termination ", e.getMessage());
		}
	}

	private void initWorker(AsynchronousSocketChannel worker)
			throws InterruptedException {
		Thread wThread = new Thread(new ServerWorker(context, worker));
		wThread.start();
	}

	public void shutdown() {
		try {
			server.close();
		} catch (IOException e) {
			log.error("Error:", e.getMessage());
		}
	}

}
