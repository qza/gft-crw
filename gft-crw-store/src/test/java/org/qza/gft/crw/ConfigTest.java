package org.qza.gft.crw;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.qza.gft.crw.store.Config;

/**
 * @author gft
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class })
public class ConfigTest {

	@Test
	public void testConfiguration() {
	}

}
