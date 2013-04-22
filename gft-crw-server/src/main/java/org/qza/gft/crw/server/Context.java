package org.qza.gft.crw.server;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import org.qza.gft.crw.ContextBase;
import org.qza.gft.crw.Message;
import org.qza.gft.crw.ServerAddress;

/**
 * @author gft
 */
public class Context extends ContextBase {

	final private Props props;

	final private Set<String> visited;

	final private Set<Message> productData;

	final private BlockingQueue<String> queue;

	final private ExecutorService executor;

	final private ScheduledExecutorService scheduler;

	public Context(final Props props, final Set<String> visited,
			final BlockingQueue<String> queue, final ExecutorService executor,
			final ScheduledExecutorService scheduler) {
		this.props = props;
		this.visited = visited;
		this.queue = queue;
		this.executor = executor;
		this.scheduler = scheduler;
		this.productData = new HashSet<>();
	}

	public synchronized void addMessage(Message message) {
		productData.add(message);
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
	
	public Set<Message> getProductDataClone(){
		return Collections.unmodifiableSet(getProductData());
	}
	
	public Collection<String> getQueueClone(){
		return Collections.unmodifiableCollection(getQueue());
	}
	
	public Collection<String> getVisitedClone(){
		return Collections.unmodifiableCollection(getVisited());
	}

}
