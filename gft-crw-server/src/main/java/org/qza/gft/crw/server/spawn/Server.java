package org.qza.gft.crw.server.spawn;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.server.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class Server implements Runnable {

	final private Logger log;

	final private Context context;

	final private ServerAddress address;

	final private List<ServerWorker> workers;

	final private AtomicInteger workerCount;

	final private ExecutorService tpool;

	private AsynchronousServerSocketChannel server;

	public Server(final Context context, final ServerAddress address) {
		this.context = context;
		this.address = address;
		this.log = LoggerFactory.getLogger(Server.class);
		this.workers = new ArrayList<>();
		this.workerCount = new AtomicInteger();
		Integer initSize = context.getProps().getServerMaxclients();
		Integer maxSize = context.getProps().getServerMaxclients();
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(
				maxSize);
		tpool = new ThreadPoolExecutor(initSize, maxSize, 10000,
				TimeUnit.MILLISECONDS, blockingQueue, threadFactory());
		this.restartServer();
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
		while (true) {
			try {
				if (isNotMaxClients()) {
					Future<AsynchronousSocketChannel> future = server.accept();
					AsynchronousSocketChannel socket = future.get();
					log.info("Connection established");
					initWorker(socket);
				} else {
					log.warn("Max clients reached");
					Thread.sleep(10000);
				}
			} catch (InterruptedException | ExecutionException e) {
				log.warn("Unexpected server termination ", e.getMessage());
				restartServer();
			}
		}
	}

	private void restartServer() {
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

	public void shutdown() {
		try {
			server.close();
		} catch (IOException e) {
			log.error("Error:", e.getMessage());
		}
	}

	private boolean isNotMaxClients() {
		return workers.size() <= context.getProps().getServerMaxclients();
	}

	private void initWorker(AsynchronousSocketChannel socket) {
		ServerWorker instance = new ServerWorker(context, workers, socket);
		workers.add(instance);
		tpool.execute(instance);
	}

	private ThreadFactory threadFactory() {
		return new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				String name = String.format("Server %s deamon %d",
						address.getPort(), workerCount.incrementAndGet());
				t.setName(name);
				t.setDaemon(true);
				return t;
			}
		};
	}

}
