package org.qza.gft.crw.store.data;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;

import javax.sql.DataSource;

/**
 * @author gft
 * 
 *         Configures and initializes main application components
 * 
 */
@Configuration
@ComponentScan(basePackages = { "org.qza.gft.crw.store" })
@PropertySource(value = { "classpath:gft-crw-store-data.properties",
		"classpath:database.properties" })
@EnableJpaRepositories
public class Config {

	@Autowired
	private Environment env;

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public DataSource datasource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setInitialSize(20);
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));;
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		return dataSource;
	}

	@Bean
	public JpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setGenerateDdl(true);
		return vendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter());
		factory.setPackagesToScan(getClass().getPackage().getName());
		factory.setDataSource(datasource());
		Properties jpaProps = new Properties();
		jpaProps.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		jpaProps.put("hibernate.hbm2ddl.auto","update");
		factory.setJpaProperties(jpaProps);
		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}
	
}
