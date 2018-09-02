package it.terrinoni.springbatchtest.processing;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatabaseConfiguration {

	@ConfigurationProperties(prefix = "spring.src.datasource")
	@Bean(name = "sourceDb")
	public DataSource sourceDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "destinationDb")
	@Primary
	@ConfigurationProperties(prefix = "spring.etl.datasource")
	public DataSource destinationDataSource() {
		return DataSourceBuilder.create().build();
	}
}