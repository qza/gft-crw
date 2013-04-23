package org.qza.gft.crw.store.repo;

import org.qza.gft.crw.store.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, String> {

}
