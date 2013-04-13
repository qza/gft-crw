package org.qza.gft.crw.server;

import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import org.qza.gft.crw.ContextBase;
import org.qza.gft.crw.address.ServerAddress;

/**
 * @author gft
 */
public class Context extends ContextBase {

	final private Props props;

	final private Set<String> visited;

	final private BlockingQueue<String> queue;

	final private ExecutorService executor;

	public Context(final Props props, final Set<String> visited,
			final BlockingQueue<String> queue, final ExecutorService executor) {
		this.props = props;
		this.visited = visited;
		this.queue = queue;
		this.executor = executor;
	}

	public synchronized void addLink(String link) {
		if (visited.add(link))
			queue.add(link);
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
		return this.executor;
	}

	public int activeTasksCount() {
		int active = -1;
		if (executor instanceof ThreadPoolExecutor) {
			active = ((ThreadPoolExecutor) executor).getActiveCount();
		}
		return active;

	}

}
