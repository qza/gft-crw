package org.qza.gft.crw.server.store;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.MessageConverter;
import org.qza.gft.crw.server.Context;
import org.qza.gft.crw.store.service.ProductStoreService;

public class DbPersister implements Runnable {

	final private Context context;

	final private ProductStoreService service;

	final private MessageConverter converter;

	public DbPersister(final Context context) {
		this.context = context;
		this.service = context.getStoreService();
		this.converter = new MessageConverter();
	}

	@Override
	public void run() {
		synchronized (DbPersister.class) {
			saveData(context.getProductDataCloneAndClear());
		}
	}

	private void saveData(Collection<Message> data) {
		service.insertAll(makeData(data));
		data.clear();
		System.gc();
	}

	private Collection<String> makeData(Collection<Message> messages) {
		Set<String> data = new HashSet<>();
		Iterator<Message> messIt = messages.iterator();
		while (messIt.hasNext()) {
			data.add(new String(converter.write(messIt.next())));
		}
		return data;
	}

}
