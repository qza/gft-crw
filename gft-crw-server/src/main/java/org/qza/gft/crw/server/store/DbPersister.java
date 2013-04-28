package org.qza.gft.crw.server.store;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.MessageConverter;
import org.qza.gft.crw.server.Context;
import org.qza.gft.crw.store.service.ProductStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbPersister implements Runnable {

	final private Context context;

	final private ProductStoreService service;

	final private MessageConverter converter;

	final private Logger log;

	public DbPersister(final Context context) {
		this.context = context;
		this.service = context.getStoreService();
		this.converter = new MessageConverter();
		this.log = LoggerFactory.getLogger(DbPersister.class);
	}

	@Override
	public void run() {
		synchronized(this){
			try {
				saveData(context.getProductDataClone());
				context.getProductData().clear();
			} catch (ConcurrentModificationException cme) {
				log.warn("Concurrent access. Will try next time.");
			} catch (Exception ex) {
				log.error("Problem saving data", ex);
			} 
		}
	}

	private void saveData(Collection<Message> data) {
		service.insertAll(makeData(data));
		data.clear();
	}

	private Collection<String> makeData(Collection<Message> messages) {
		Set<String> data = new HashSet<>();
		try {
			Iterator<Message> messIt = messages.iterator();
			while (messIt.hasNext()) {
				data.add(new String(converter.write(messIt.next())));
			}
		} catch(ConcurrentModificationException cme) {
			data.clear();
			throw new ConcurrentModificationException(cme);
		} 
		return data;
	}

}
