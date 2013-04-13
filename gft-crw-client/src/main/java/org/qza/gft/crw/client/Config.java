package org.qza.gft.crw.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
@ComponentScan(basePackages = { "org.qza.gft.crw.client" })
@PropertySource("classpath:gft-crw-client.properties")
public class Config {

	@Autowired
	private Environment env;

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Props props() {
		Props props = new Props();
		props.setLogTime(env.getProperty("crawler.log.time"));
		props.setLogCompleted(env.getProperty("crawler.log.completed"));
		props.setParserCss(env.getProperty("crawler.parser.css"));
		props.setParserTimeout(env.getProperty("crawler.parser.timeout"));
		props.setParserMaxbytes(env.getProperty("crawler.parser.maxbytes"));
		props.setServerList(env.getProperty("crawler.server.list"));
		props.setClientDuration(env.getProperty("crawler.client.duration"));
		props.setClientTimeout(env.getProperty("crawler.client.timeout"));
		props.setSpawnTime(env.getProperty("crawler.spawn.time"));
		props.setSpawnPerserver(env.getProperty("crawler.spawn.perserver"));
		props.setTpoolInitsize(env.getProperty("crawler.spawn.tpool.initsize"));
		props.setTpoolMaxsize(env.getProperty("crawler.spawn.tpool.maxsize"));
		return props;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Context context() {
		Context context = new Context(props());
		return context;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public ExecutorService executor() {
		Integer initSize = props().getTpoolInitsize();
		Integer maxSize = props().getTpoolMaxsize();
		BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
		ExecutorService tpool = new ThreadPoolExecutor(initSize, maxSize,
				10000, TimeUnit.MILLISECONDS, blockingQueue);
		return tpool;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Spawner spawner() {
		Spawner spawner = new Spawner(context(), executor());
		return spawner;
	}

}
