package org.qza.gft.crw.store.impo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.qza.gft.crw.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author gft
 * 
 *         Imports test data into configured database and collection
 */
public class DataImport {

	private final Logger log;

	private final DBCollection productCollection;

	public DataImport(final DBCollection productCollection) {
		this.log = LoggerFactory.getLogger(DataImport.class);
		this.productCollection = productCollection;
	}

	public void importCollection(String dataFileName) {
		Set<String> data = new HashSet<>();
		FileUtils.load(dataFileName, data);
		importCollection(data);
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
