package org.qza.gft.crw.server.spawn;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class NioServer implements Runnable {

	final private Logger log;

	final private ExecutorService tpool;

	final private Context context;

	final private ServerAddress address;

	final private AsynchronousChannelGroup group;

	final private AsynchronousServerSocketChannel server;

	final private List<NioChannel> channels;

	public NioServer(final Context context, final ServerAddress address) {
		this.context = context;
		this.address = address;
		this.channels = new ArrayList<>();
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
			log.info(String.format("Started @ %s : %d", address.getHost(),
					address.getPort()));
		} catch (Exception ex) {
			log.error(String.format("Cannot start server @ %s : %d",
					address.getHost(), address.getPort()));
			throw new RuntimeException(ex);
		}
	}
	
	public void addChannel(AsynchronousSocketChannel channel) {
		NioChannel ch = new NioChannel(context, channel);
		channels.add(ch);
	}

	@Override
	public void run() {
		server.accept(null, handler());
		while(true) {
			synchronized(channels) {
				for (int i = 0; i < channels.size(); i++) {
					try {
						channels.get(i).react();
					}
					catch(Exception ex) {
						log.error(ex.getMessage());
					}
				}
			}
		}
	}
	
	public CompletionHandler<AsynchronousSocketChannel, Void> handler() {
		return new CompletionHandler<AsynchronousSocketChannel, Void>() {
			public void completed(AsynchronousSocketChannel ch, Void att) {
				server.accept(null, this);
				addChannel(ch);
				log.info("Connection established");
			}
			public void failed(Throwable th, Void att) {
				log.warn("Unexpected connection termination : "
						+ th.getMessage());
			}
		};
	}

	public void shutdown() {
		closeChannels();
		closeServer();
	}
	
	public void closeChannels() {
		Iterator<NioChannel> it = channels.iterator();
		while (it.hasNext()) {
			it.next().shutdown();
		}
		channels.clear();
	}
	
	public void closeServer() {
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
