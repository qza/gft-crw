package org.qza.gft.crw.store;

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
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
	public Props storeProps() {
		Props props = new Props();
		props.setLogTime(getProperty("crawler.store.log.time"));
		props.setDbHost(getProperty("crawler.store.host"));
		props.setDbPort(getProperty("crawler.store.port"));
		props.setMaxClients(getProperty("crawler.store.maxclients"));
		props.setStoreName(getProperty("crawler.store.name"));
		props.setCollectionProducts(getProperty("crawler.store.collections.product"));
		props.setDataInitFile(getProperty("crawler.store.data.initfile"));
		return props;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Context storeContext() {
		Context context = new Context(storeProps());
		return context;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public Mongo storeMongo() {
		try {
			return new Mongo(storeProps().getDbHost(), storeProps().getDbPort());
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (MongoException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public DB storeDb() {
		try {
			return storeMongo().getDB(storeProps().getStoreName());
		} catch (MongoException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public DBCollection productCollection() {
		return storeDb().getCollection(storeProps().getCollectionProducts());
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public DataImport storeImporter() {
		DataImport importer = new DataImport(productCollection());
		return importer;
	}

}
