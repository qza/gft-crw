package org.qza.gft.crw.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.qza.gft.crw.store.impo.DataImport;

/**
 * @author gft
 */
public class Runner {

	final private DataImport importer;

	final private Props props;

	final private ApplicationContext ac;

	final private Logger log = LoggerFactory.getLogger(Runner.class);

	/**
	 * Creates new Runner instance
	 */
	public Runner() {
		ac = new AnnotationConfigApplicationContext(Config.class);
		importer = ac.getBean(DataImport.class);
		props = ac.getBean(Props.class);
	}

	/**
	 * Starts crawling servers
	 */
	public void start() {
		try {
			log.info("Importing data");
			importer.importCollection(props.getDataInitFile());
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
