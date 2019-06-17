package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class BatchConfiguration {

    @Bean
    public Job batchJob(JobBuilderFactory jobBuilders, StepBuilderFactory stepBuilders) {
        return jobBuilders.get("helloWorldJob").start(batchStep(stepBuilders)).build();
    }

    @Bean
    public Step batchStep(StepBuilderFactory stepBuilders) {
        return stepBuilders.get("helloWorldStep")
                .<BatchItem, String>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    @Bean
    public FlatFileItemReader<BatchItem> reader(){
        return new FlatFileItemReaderBuilder<BatchItem>()
                .name("Flat File Reader")
                .resource(new ClassPathResource("input.csv"))
                .delimited().names(new String[] {"firstName", "lastName"})
                .targetType(BatchItem.class).build();
    }

    @Bean
    public ItemProcessor processor(){
        return (ItemProcessor<BatchItem, String>) batchItem -> {

            System.out.println("Processing : " + batchItem.toString());

            return "Processed  " + batchItem.getFirstName() + " " + batchItem.getLastName();
        };
    }

    @Bean
    public FlatFileItemWriter<String> writer(){
        return new FlatFileItemWriterBuilder<String>()
                .name("Flat File Writer")
                .resource(new FileSystemResource("output.txt"))
                .lineAggregator(new PassThroughLineAggregator<>()).build();

    }



}
