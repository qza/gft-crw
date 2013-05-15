package org.qza.gft.crw.server.spawn;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.server.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gft
 */
public class NioServerWithWorker implements Runnable {

	final private Logger log;

	final private ExecutorService tpool;

	final private Context context;

	final private ServerAddress address;

	final private AsynchronousChannelGroup group;

	final private AsynchronousServerSocketChannel server;

	final private NioServerWorker worker;

	public NioServerWithWorker(final Context context, final ServerAddress address) {
		this.context = context;
		this.address = address;
		this.log = LoggerFactory.getLogger(Server.class);
		Integer initSize = context.getProps().getServerMaxclients();
		Integer maxSize = context.getProps().getServerMaxclients();
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(
				maxSize);
		this.tpool = new ThreadPoolExecutor(initSize, maxSize, 10000,
				TimeUnit.MILLISECONDS, blockingQueue, threadFactory());
		SocketAddress sAddress = new InetSocketAddress(address.getHost(),
				address.getPort());
		try {
			this.group = AsynchronousChannelGroup.withThreadPool(this.tpool);
			this.server = AsynchronousServerSocketChannel.open(group).bind(
					sAddress);
			this.worker = new NioServerWorker(context);
			log.info(String.format("Started @ %s : %d", address.getHost(),
					address.getPort()));
		} catch (Exception ex) {
			log.error(String.format("Cannot start server @ %s : %d",
					address.getHost(), address.getPort()));
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void run() {
		context.execute(worker);
		server.accept(null, handler());
		try {
			this.group.awaitTermination(context.getProps().getServerDuration(),
					TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			Thread.interrupted();
		}
	}

	public CompletionHandler<AsynchronousSocketChannel, Void> handler() {
		return new CompletionHandler<AsynchronousSocketChannel, Void>() {
			public void completed(AsynchronousSocketChannel ch, Void att) {
				server.accept(null, this);
				log.info("Connection established");
				worker.addChannel(ch);
			}

			public void failed(Throwable th, Void att) {
				log.warn("Unexpected connection termination : "
						+ th.getMessage());
			}
		};
	}

	public void shutdown() {
		try {
			server.close();
			tpool.shutdownNow();
			tpool.awaitTermination(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("Error: " + e.getMessage());
		}
	}

	private ThreadFactory threadFactory() {
		return new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				String name = String.format("nio pool deamon %s",
						address.getPort());
				t.setName(name);
				t.setDaemon(true);
				return t;
			}
		};
	}

}
