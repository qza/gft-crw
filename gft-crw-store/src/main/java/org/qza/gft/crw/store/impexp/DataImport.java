package org.qza.gft.crw.store.impexp;

import java.net.UnknownHostException;

import java.util.Collection;
import java.util.Iterator;

import org.qza.gft.crw.store.Context;
import org.qza.gft.crw.store.Props;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class DataImport {

	private final Props props;

	private final Mongo mongo;

	private final DB db;

	private final Logger log;

	private final DBCollection productCollection;

	public DataImport(final Context context) {
		this.props = context.getProps();
		this.log = LoggerFactory.getLogger(DataImport.class);
		try {
			this.mongo = new Mongo(props.getDbHost(), props.getDbPort());
			this.db = mongo.getDB(props.getStoreName());
			this.productCollection = db.getCollection(props
					.getCollectionProducts());
		} catch (UnknownHostException | MongoException e) {
			String error = "Not able to connect to mongo instance";
			log.error(error);
			throw new RuntimeException(error);
		}
	}

	public void persist(Collection<String> data) {
		for (Iterator<String> iterator = data.iterator(); iterator.hasNext();) {
			String row = iterator.next();
			DBObject dbObject = (DBObject) JSON.parse(row);
			if (dbObject != null) {
				productCollection.insert(dbObject);
			} else {
				log.error("Db object is null for ROW ::: " + row);
			}
		}
	}

}
