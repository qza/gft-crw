package org.qza.gft.crw.store;

import java.util.HashSet;
import java.util.Set;

import org.qza.gft.crw.FileUtils;
import org.qza.gft.crw.store.impo.DataImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gft
 */
public class Runner {

	final private DataImport importer;

	final private ApplicationContext ac;

	final private Logger log = LoggerFactory.getLogger(Runner.class);

	/**
	 * Creates new Runner instance
	 */
	public Runner() {
		String config = "classpath:/**/config.xml";
		ac = new ClassPathXmlApplicationContext(config);
		importer = ac.getBean(DataImport.class);
	}

	/**
	 * Starts crawling servers
	 */
	public void start() {
		try {
			Set<String> data = new HashSet<>();
			log.info("Loading data");
			FileUtils.loadData("src/main/resources/data/test_data.txt", data);
			log.info("Persisting data");
			importer.importCollection(data);
			log.info("Completed");
		} catch (Exception e) {
			log.error("Error: ", e);
		} finally {
			log.info("Exiting");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new Runner().start();
	}

}
