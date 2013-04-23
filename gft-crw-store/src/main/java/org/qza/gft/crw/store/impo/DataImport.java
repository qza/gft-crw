package org.qza.gft.crw.store.impo;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author gft
 * 
 *         Imports test data into product_store:products collection
 */
public class DataImport {

	private final Logger log;

	private final DBCollection productCollection;

	public DataImport(final DBCollection productCollection) {
		this.log = LoggerFactory.getLogger(DataImport.class);
		this.productCollection = productCollection;
	}

	public void importCollection(Collection<String> data) {
		for (Iterator<String> iterator = data.iterator(); iterator.hasNext();) {
			String row = iterator.next();
			DBObject dbObject = (DBObject) JSON.parse(row);
			if (dbObject != null) {
				productCollection.insert(dbObject);
			} else {
				log.error("Db object is null for row: " + row);
			}
		}
	}

}
