package org.qza.gft.crw.store.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.qza.gft.crw.store.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

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
			int perpage) {
		BasicDBObject query = new BasicDBObject(name, value);
		return executeLoad(query, page, perpage);
	}

	@Override
	public List<Product> fetchAll(Map<String, Object> conditions, int page,
			int perpage) {
		BasicDBObject query = buildQuery(conditions);
		return executeLoad(query, page, perpage);
	}

	@Override
	public void save(Product product) {
		String productStr = mapper.write(product);
		DBObject productDb = (DBObject) JSON.parse(productStr);
		productCollection.save(productDb);
	}

	@Override
	public boolean delete(Product entity) {
		return false;
	}
	
	@Override
	public boolean delete(int[] ids) {
		for (int i = 0; i < ids.length; i++) {
			productCollection.remove(new BasicDBObject("_id", ids[i]));
		}
		return true;
	}
	
	@Override
	public boolean batchUpdate(int[] ids, String key, Object value) {
		for (int i = 0; i < ids.length; i++) {
			BasicDBObject search = new BasicDBObject().append("_id", ids[i]);
			BasicDBObject update = new BasicDBObject("$set", new BasicDBObject(key, value));
			productCollection.update(search, update);
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

	private List<Product> executeLoad(BasicDBObject query, int page, int perpage) {
		List<Product> results = new ArrayList<>();
		cursorToList(productCollection.find(query), results, page, perpage);
		return results;
	}

	private void cursorToList(DBCursor cursor, List<Product> list, int page,
			int perpage) {
		cursor = cursor.skip((page - 1) * perpage).limit(perpage);
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
