package org.qza.gft.crw.server;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.qza.gft.crw.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

/**
 * @author gft
 */
@Configuration
@ComponentScan(basePackages = { "org.qza.gft.crw.server" })
@PropertySource("classpath:gft-crw-server.properties")
public class Config {

	@Autowired
	private Environment env;

	private String getProperty(String key) {
		return env.getProperty(key);
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Props props() {
		Props props = new Props();
		props.setQueueMaxsize(getProperty("crawler.queue.maxsize"));
		props.setQueueInitfile(getProperty("crawler.queue.initfile"));
		props.setVisitedMaxsize(getProperty("crawler.visited.maxsize"));
		props.setLogTime(getProperty("crawler.log.time"));
		props.setLogCompleted(getProperty("crawler.log.completed"));
		props.setServerTimeout(getProperty("crawler.server.timeout"));
		props.setServerDuration(getProperty("crawler.server.duration"));
		props.setServerHost(getProperty("crawler.server.host"));
		props.setServerPorts(getProperty("crawler.server.ports"));
		props.setTpoolInitsize(getProperty("crawler.spawn.tpool.initsize"));
		props.setTpoolMaxsize(getProperty("crawler.spawn.tpool.maxsize"));
		props.setReportFileMain(getProperty("crawler.report.file.main"));
		props.setReportFileQueue(getProperty("crawler.report.file.queue"));
		props.setReportFileVisited(getProperty("crawler.report.file.visited"));
		props.setReportLogInterval(getProperty("crawler.report.log.interval"));
		props.setServerMaxclients(getProperty("crawler.server.maxclients"));
		return props;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public ExecutorService executor() {
		Integer initSize = props().getTpoolInitsize();
		Integer maxSize = props().getTpoolMaxsize();
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(
				maxSize);
		ExecutorService tpool = new ThreadPoolExecutor(initSize, maxSize,
				10000, TimeUnit.MILLISECONDS, blockingQueue);
		return tpool;
	}
	
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public ScheduledExecutorService scheduler() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		return scheduler;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public BlockingQueue<String> queue() {
		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(props()
				.getQueueMaxsize());
		FileUtils.loadData(props().getQueueInitfile(), queue);
		return queue;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Set<String> visited() {
		Set<String> visited = new HashSet<>(props().getVisitedMaxsize());
		return visited;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Context context() {
		Context context = new Context(props(), visited(), queue(), executor(), scheduler());
		return context;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Spawner spawner() {
		Spawner spawner = new Spawner(context());
		return spawner;
	}
	
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Reporter reporter() {
		Reporter reporter = new Reporter(context());
		return reporter;
	}

}
