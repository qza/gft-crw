package org.qza.gft.crw.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author gft
 */
public class Runner {

	final private Spawner spawner;

	final private ApplicationContext ac;

	final private Logger log = LoggerFactory.getLogger(Runner.class);

	/**
	 * Creates new Runner instance
	 */
	public Runner() {
		ac = new AnnotationConfigApplicationContext(Config.class);
		spawner = ac.getBean(Spawner.class);
	}

	/**
	 * Starts the crawling
	 */
	public void start() {
		try {
			log.info("Starting crawling ");
			spawner.spawn();
			log.info("Crawling completed ");
		} catch (Exception e) {
			log.error("Error: ", e);
		} finally {
			log.info("Crawling ended.");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new Runner().start();
	}

}
