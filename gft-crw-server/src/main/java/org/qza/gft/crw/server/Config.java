package org.qza.gft.crw.server;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.server.report.Reporter;
import org.qza.gft.crw.server.store.DbPersister;
import org.qza.gft.crw.store.data.service.product.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

/**
 * @author gft
 */
@Configuration
@ComponentScan(basePackages = { "org.qza.gft.crw" })
@PropertySource("classpath:gft-crw-server.properties")
@Import(org.qza.gft.crw.store.data.Config.class)
public class Config {

	@Autowired
	private Environment env;

	@Autowired
	private ProductService store;

	private String getProperty(String key) {
		return env.getProperty(key);
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Props props() {
		Props props = new Props();
		props.setQueueMaxsize(getProperty("crawler.queue.maxsize"));
		props.setVisitedMaxsize(getProperty("crawler.visited.maxsize"));
		props.setLogTime(getProperty("crawler.log.time"));
		props.setLogCompleted(getProperty("crawler.log.completed"));
		props.setServerDuration(getProperty("crawler.server.duration"));
		props.setServerHost(getProperty("crawler.server.host"));
		props.setServerPorts(getProperty("crawler.server.ports"));
		props.setTpoolInitsize(getProperty("crawler.spawn.tpool.initsize"));
		props.setTpoolMaxsize(getProperty("crawler.spawn.tpool.maxsize"));
		props.setReportFilename(getProperty("crawler.report.filename"));
		props.setDataFileQueue(getProperty("crawler.data.file.queue"));
		props.setDataFileVisited(getProperty("crawler.data.file.visited"));
		props.setDataFileProducts(getProperty("crawler.data.file.main"));
		props.setDataPersistInterval(getProperty("crawler.data.persist.interval"));
		props.setReportLogInterval(getProperty("crawler.report.log.interval"));
		props.setServerMaxclients(getProperty("crawler.server.maxclients"));
		props.setDataMemoryMin(getProperty("crawler.data.memory.min"));
		props.setBanCategories(getProperty("crawler.ban.categories"));
		return props;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public ThreadFactory threadFactory() {
		return new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName("Server thread");
				return t;
			}
		};
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public ExecutorService executor() {
		Integer initSize = props().getTpoolInitsize();
		Integer maxSize = props().getTpoolMaxsize();
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(
				maxSize);
		ExecutorService tpool = new ThreadPoolExecutor(initSize, maxSize,
				10000, TimeUnit.MILLISECONDS, blockingQueue, threadFactory());
		return tpool;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public ScheduledExecutorService scheduler() {
		ScheduledExecutorService scheduler = Executors
				.newSingleThreadScheduledExecutor();
		return scheduler;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public BlockingQueue<String> queue() {
//		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(props()
//				.getQueueMaxsize());
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		return queue;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Set<String> visited() {
//		Set<String> visited = new HashSet<>(props().getVisitedMaxsize());
		Set<String> visited = new HashSet<>();
		return visited;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Set<String> collected() {
//		Set<String> collected = new HashSet<>(props().getVisitedMaxsize());
		Set<String> collected = new HashSet<>();
		return collected;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Set<Message> products() {
		Set<Message> products = new HashSet<>();
		return products;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Acceptor acceptor() {
		Acceptor acceptor = new Acceptor(props());
		return acceptor;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public DbPersister dbPersister() {
		DbPersister persister = new DbPersister(context());
		return persister;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Context context() {
		Context context = new Context(props(), visited(), queue(), executor(),
				scheduler(), products(), collected(), acceptor(), store);
		return context;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Initializer initializer() {
		Initializer initializer = new Initializer(context());
		return initializer;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Spawner spawner() {
		Spawner spawner = new Spawner(context(), initializer());
		return spawner;
	}
	
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public NioSpawner nioSpawner() {
		NioSpawner spawner = new NioSpawner(context(), initializer());
		return spawner;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Reporter reporter() {
		Reporter reporter = new Reporter(context());
		return reporter;
	}

}
