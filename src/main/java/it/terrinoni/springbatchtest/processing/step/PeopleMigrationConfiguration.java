package it.terrinoni.springbatchtest.processing.step;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.terrinoni.springbatchtest.model.Person;

@Configuration
public class PeopleMigrationConfiguration {

	private static final Log log = LogFactory.getLog(SetupConfiguration.class);

	@Bean
	public JdbcCursorItemReader<Person> jdbcReader(DataSource ds) {
		return new JdbcCursorItemReaderBuilder<Person>().dataSource(ds).name("jdbc-reader")
				.sql("SELECT first_name fn, age a, email e FROM people ORDER BY first_name ASC")
				.rowMapper((rs, i) -> new Person(rs.getString("fn"), rs.getInt("a"), rs.getString("e"))).build();
	}

	@Bean
	public ItemProcessor<Person, Person> checker() {
		return item -> {
			log.info("Processing current item: " + item.toString());
			return item;
		};
	}

	@Bean
	public JdbcBatchItemWriter<Person> jdbcWriter(DataSource ds) {
		return new JdbcBatchItemWriterBuilder<Person>().dataSource(ds)
				.sql("INSERT INTO people(`first_name`, `age`, `email`) VALUES (:firstName, :age, :email)").beanMapped()
				.build();
	}
}
