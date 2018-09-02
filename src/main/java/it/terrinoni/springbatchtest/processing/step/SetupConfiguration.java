package it.terrinoni.springbatchtest.processing.step;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SetupConfiguration {

	private static final Log log = LogFactory.getLog(SetupConfiguration.class);

	@Bean
	public Tasklet tasklet(DataSource ds) {
		return (contribution, chunkContext) -> {
			log.info("Start cleaning up ETL People table");
			JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
			jdbcTemplate.update("DELETE FROM people");
			return RepeatStatus.FINISHED;
		};
	}

}
