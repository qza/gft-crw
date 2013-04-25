package org.qza.gft.crw;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.qza.gft.crw.store.Config;
import org.qza.gft.crw.store.entity.Product;
import org.qza.gft.crw.store.service.Page;
import org.qza.gft.crw.store.service.ProductStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class })
public class ProductStoreServiceTest {

	@Autowired
	private ProductStoreService service;
	
	private Product p;
	
	@Before
	public void initializeProduct(){
		p = new Product();
		p.setName("aaaa");
		p.setCategory("bbbbb");
		p.setImage("cccccc");
		p.setPrice("sssss");
		p.setRating("vvvvvv");
		p.setRelated(new HashSet<String>(Arrays.asList("fgfgfgfgfgfg", "fgfgfgfgfgfgfg")));
		p.setTags(new HashSet<String>(Arrays.asList("tag1", "tag2","tag3", "tag4")));
	}
	
	@Test
	public void testPersist() {
		service.persist(p);
	}

	@Test
	public void testFetchAll() {
		List<Product> products = service.fetchAll(new Page(1));
		assertNotNull(products);
		assertTrue(products.size() > 0);
		assertTrue(products.iterator().next().get_id()!=null);
	}

	@Test
	public void testFindByName() {
		List<Product> products = service.findByName("aaaa", new Page(1));
		assertNotNull(products);
		assertTrue(products.size() > 0);
	}

}
