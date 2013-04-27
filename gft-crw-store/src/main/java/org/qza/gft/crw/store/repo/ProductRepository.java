package org.qza.gft.crw.store.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.qza.gft.crw.store.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author g
 */
@Component
public class ProductRepository implements Repository<Product> {

	@Autowired
	private DBCollection productCollection;

	private ProductMapper mapper;

	public ProductRepository() {
		mapper = new ProductMapper();
	}

	@Override
	public List<Product> findByAttribute(String name, String value, int page,
			int size) {
		BasicDBObject query = new BasicDBObject(name, value);
		return loadData(query, page, size);
	}

	@Override
	public List<Product> fetchAll(Map<String, Object> conditions, int page,
			int size) {
		BasicDBObject query = null;
		if (conditions != null && conditions.size() > 0) {
			query = buildQuery(conditions);
		}
		return loadData(query, page, size);
	}

	@Override
	public String save(Product product) {
		String productStr = mapper.write(product);
		DBObject productDb = (DBObject) JSON.parse(productStr);
		productCollection.save(productDb);
		ObjectId id = (ObjectId) ((ObjectId) productDb.get("_id"));
		product.set_id(id);
		return id.toString();
	}

	@Override
	public boolean delete(Product entity) {
		return false;
	}

	@Override
	public boolean deleteAll(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			productCollection.remove(new BasicDBObject("_id", ids[i]));
		}
		return true;
	}

	@Override
	public boolean deleteAll(String key, String val) {
		productCollection.remove(new BasicDBObject(key, val));
		return true;
	}

	@Override
	public boolean updateAll(String[] ids, String key, Object value) {
		for (int i = 0; i < ids.length; i++) {
			BasicDBObject search = new BasicDBObject().append("_id",
					new ObjectId(ids[i]));
			DBObject dbObj = productCollection.findOne(search);
			dbObj.put(key, value);
			productCollection.save(dbObj);
		}
		return true;
	}

	@Override
	public boolean updateAll(int page, int size, String key, Object value, DBObject criteria) {
		DBCursor cursor = productCollection.find(criteria);
		getPosition(cursor, page, size);
		while (cursor.hasNext()) {
			DBObject dbObj = cursor.next();
			dbObj.put(key, value);
			productCollection.save(dbObj);
		}
		return true;
	}

	private BasicDBObject buildQuery(Map<String, Object> conditions) {
		BasicDBObject andQuery = null;
		if (conditions != null) {
			andQuery = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject(conditions));
			andQuery.put("$and", obj);
		}
		return andQuery;
	}

	private List<Product> loadData(BasicDBObject query, int page, int size) {
		List<Product> results = new ArrayList<>();
		cursorToList(productCollection.find(query), results, page, size);
		return results;
	}

	private void cursorToList(DBCursor cursor, List<Product> list, int page,
			int size) {
		getPosition(cursor, page, size);
		convertPositionedCursor(cursor, list);
	}

	private DBCursor getPosition(DBCursor cursor, int page, int size) {
		return cursor.skip((page - 1) * size).limit(size);
	}

	private void convertPositionedCursor(DBCursor cursor, List<Product> list) {
		try {
			while (cursor.hasNext()) {
				String row_data = JSON.serialize(cursor.next());
				list.add(mapper.read(row_data));
			}
		} finally {
			cursor.close();
		}
	}

}
