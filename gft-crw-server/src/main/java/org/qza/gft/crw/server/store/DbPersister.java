package org.qza.gft.crw.server.store;

import java.util.Collection;
import java.util.ConcurrentModificationException;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.server.Context;
import org.qza.gft.crw.store.data.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbPersister implements Runnable {

	final private Context context;

	final private ProductService service;

	final private Logger log;

	final private Integer memoryMin;

	final private Integer mb = 1024 * 1024;

	public DbPersister(final Context context) {
		this.context = context;
		this.service = context.getStoreService();
		this.memoryMin = context.getProps().getDataMemoryMin();
		this.log = LoggerFactory.getLogger(DbPersister.class);
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				saveData(context.getProductDataClone());
				context.getProductData().clear();
			}
		} catch (ConcurrentModificationException cme) {
			log.warn("Concurrent access. Will try next time.");
		} catch (Exception ex) {
			log.error("Problem saving data", ex);
		} catch (Throwable th) {
			log.error("Problem saving data", th);
		} finally {
			checkMemory();
		}
	}

	private void saveData(Collection<Message> data) {
		service.insertAll(data);
		data.clear();
	}

	private void checkMemory() {
		long free = Runtime.getRuntime().freeMemory() / mb;
		if (free < memoryMin / 2) {
			log.warn("\n Running out of memory!!!\n");
		}
		if (free < memoryMin) {
			System.gc();
		}
	}

}
