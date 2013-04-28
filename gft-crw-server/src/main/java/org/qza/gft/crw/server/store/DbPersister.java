package org.qza.gft.crw.server.store;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.qza.gft.crw.Message;
import org.qza.gft.crw.MessageConverter;
import org.qza.gft.crw.store.service.ProductStoreService;

public class DbPersister {

	final private ProductStoreService service;

	final private MessageConverter converter;

	public DbPersister(final ProductStoreService service) {
		this.service = service;
		this.converter = new MessageConverter();
	}

	public void saveData(Collection<Message> data) {
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
