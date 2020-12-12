package umut.backend.Batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import umut.backend.Batch.PriceParser.CustomProductModel;
import umut.backend.Batch.PriceParser.PriceParserReader;
import umut.backend.Batch.PriceParser.PriceParserWriter;
import umut.backend.Batch.PriceParser.PriceParserWriterListener;
import umut.backend.Batch.Ps5Finder.Models.ProductAvailability;
import umut.backend.Batch.Ps5Finder.Ps5FinderReader;
import umut.backend.Batch.Ps5Finder.Ps5FinderWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job priceParserJob(@Qualifier("priceParserSteps") Step step, JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("price-parser-job")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step priceParserSteps(StepBuilderFactory stepBuilderFactory, PriceParserWriter writer, PriceParserReader reader, PriceParserWriterListener writerListener) {
        return stepBuilderFactory.get("price-parser-step")
                .<CustomProductModel, CustomProductModel>chunk(100)
                .reader(reader)
                .processor(new PassThroughItemProcessor<>())
                .writer(writer)
                .listener(writerListener)
                .build();
    }

    @Bean
    public Job ps5FinderJob(@Qualifier("ps5FinderSteps") Step step, JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("ps5finder-job")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step ps5FinderSteps(StepBuilderFactory stepBuilderFactory, Ps5FinderWriter writer, Ps5FinderReader reader) {
        return stepBuilderFactory.get("ps5-finder-step")
                .<ProductAvailability, ProductAvailability>chunk(1)
                .reader(reader)
                .processor(new PassThroughItemProcessor<>())
                .writer(writer)
                .build();
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        jobLauncher.setTaskExecutor(simpleAsyncTaskExecutor);
        return jobLauncher;
    }

}
