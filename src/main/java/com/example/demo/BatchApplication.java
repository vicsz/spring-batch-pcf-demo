package com.example.demo;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.sql.DataSource;

@EnableBatchProcessing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BatchApplication extends DefaultBatchConfigurer {

	@Override
	public void setDataSource(DataSource dataSource) {
		// This will cause usage of in-memory May for JobRepository storage instead of JDBC
	}

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

}
