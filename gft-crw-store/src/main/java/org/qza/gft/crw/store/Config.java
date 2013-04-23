package org.qza.gft.crw.store;

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import java.net.UnknownHostException;

import org.qza.gft.crw.store.impo.DataImport;

/**
 * @author gft
 * 
 *         Configures and initializes main application object
 * 
 */
@Configuration
@ComponentScan(basePackages = { "org.qza.gft.crw.store" })
@PropertySource("classpath:gft-crw-store.properties")
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
		props.setLogTime(getProperty("crawler.store.log.time"));
		props.setDbHost(getProperty("crawler.store.host"));
		props.setDbPort(getProperty("crawler.store.port"));
		props.setMaxClients(getProperty("crawler.store.maxclients"));
		props.setStoreName(getProperty("crawler.store.name"));
		props.setCollectionProducts(getProperty("crawler.store.collections.product"));
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
	public Mongo mongo() {
		try {
			return new Mongo(props().getDbHost(), props().getDbPort());
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (MongoException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public DB db() {
		try {
			return mongo().getDB(props().getStoreName());
		} catch (MongoException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean(name="mongoTemplate")
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongo(), db().getName());
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public DBCollection productCollection() {
		return db().getCollection(props().getCollectionProducts());
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public DataImport importer() {
		DataImport importer = new DataImport(productCollection());
		return importer;
	}

}
