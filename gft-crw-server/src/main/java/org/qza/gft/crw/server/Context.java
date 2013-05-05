package org.qza.gft.crw.server;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import org.qza.gft.crw.ContextBase;
import org.qza.gft.crw.Message;
import org.qza.gft.crw.ServerAddress;
import org.qza.gft.crw.store.data.service.product.ProductService;

/**
 * @author gft
 */
public class Context extends ContextBase {

	final private Props props;

	final private Set<String> visited;

	final private Set<String> collected;

	final private Set<Message> productData;

	final private BlockingQueue<String> queue;

	final private ExecutorService executor;

	final private ScheduledExecutorService scheduler;

	final private ProductService storeService;

	final private Acceptor acceptor;
	
	public Context(final Props props, final Set<String> visited,
			final BlockingQueue<String> queue, final ExecutorService executor,
			final ScheduledExecutorService scheduler,
			final Set<Message> products, final Set<String> collected,
			final Acceptor acceptor, final ProductService storeService) {
		this.props = props;
		this.visited = visited;
		this.collected = collected;
		this.queue = queue;
		this.executor = executor;
		this.scheduler = scheduler;
		this.productData = products;
		this.storeService = storeService;
		this.acceptor = acceptor;
	}

	public synchronized void addMessage(Message message) {
		if (acceptor.accept(message)) {
			if (collected.add(message.getUrl())) {
				productData.add(message);
			}
		}
		for (Iterator<String> iterator = message.getRelated().iterator(); iterator
				.hasNext();) {
			String link = iterator.next();
			if (visited.add(link)) {
				queue.add(link);
			}
		}
	}

	public Set<String> getVisited() {
		return visited;
	}

	public Set<String> getCollected() {
		return collected;
	}

	public BlockingQueue<String> getQueue() {
		return queue;
	}

	public List<ServerAddress> getServerList() {
		return props.getServerList();
	}

	public int visitedSize() {
		return getVisited().size();
	}

	public int queueSize() {
		return getQueue().size();
	}

	public int productsSize() {
		return getProductData().size();
	}

	public int collectedSize() {
		return getCollected().size();
	}

	public boolean isMaxSize() {
		return getProps().getVisitedMaxsize() >= visitedSize();
	}

	public Props getProps() {
		return props;
	}

	public void execute(Runnable task) {
		executor.execute(task);
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public ScheduledExecutorService getScheduler() {
		return scheduler;
	}

	public int activeTasksCount() {
		int active = -1;
		if (isThreadPool()) {
			active = getThreadPool().getActiveCount();
		}
		return active;
	}

	public long completedTasksCount() {
		long completed = -1;
		if (isThreadPool()) {
			completed = getThreadPool().getCompletedTaskCount();
		}
		return completed;
	}

	private boolean isThreadPool() {
		return executor instanceof ThreadPoolExecutor;
	}

	private ThreadPoolExecutor getThreadPool() {
		return (ThreadPoolExecutor) executor;
	}

	public Set<Message> getProductData() {
		return productData;
	}

	public void addProductData(Message data) {
		getProductData().add(data);
	}

	public List<Message> getProductDataClone() {
		List<Message> data = new CopyOnWriteArrayList<>(getProductData());
		return data;
	}

	public List<String> getQueueClone() {
		return new CopyOnWriteArrayList<>(getQueue());
	}

	public List<String> getVisitedClone() {
		return new CopyOnWriteArrayList<>(getVisited());
	}

	public ProductService getStoreService() {
		return storeService;
	}

}
