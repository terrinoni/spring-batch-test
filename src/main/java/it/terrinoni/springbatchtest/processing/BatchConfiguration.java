package it.terrinoni.springbatchtest.processing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.terrinoni.springbatchtest.model.Person;
import it.terrinoni.springbatchtest.processing.step.PeopleMigrationConfiguration;
import it.terrinoni.springbatchtest.processing.step.SetupConfiguration;

@Configuration
public class BatchConfiguration {

	@Autowired
	DatabaseConfiguration dc;

	@Bean
	Job etl(JobBuilderFactory jbf, StepBuilderFactory sbf, SetupConfiguration setupConfig,
			PeopleMigrationConfiguration migratePeopleTableConfig) {

		Step setup = sbf.get("clean-etl-people-table").tasklet(setupConfig.tasklet(dc.destinationDataSource())).build();

		Step migratePeopleTableStep = sbf.get("migrate-people-table").<Person, Person>chunk(10)
				.reader(migratePeopleTableConfig.jdbcReader(dc.sourceDataSource()))
				.processor(migratePeopleTableConfig.checker())
				.writer(migratePeopleTableConfig.jdbcWriter(dc.destinationDataSource())).build();

		return jbf.get("etl").incrementer(new RunIdIncrementer()).start(setup).next(migratePeopleTableStep).build();
	}

}
