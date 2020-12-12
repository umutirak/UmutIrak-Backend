package umut.backend.Batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class BatchRunner {

    private final JobLauncher jobLauncher;
    @Qualifier("priceParserJob")
    private final Job priceParserJob;
    @Qualifier("ps5FinderJob")
    private final Job ps5FinderJob;

    @Scheduled(initialDelay = 1000 * 10, fixedDelayString = "${app.priceParser.fixedDelay}")
    public void performPriceParser() throws Exception {
        log.info("Starting Price Parser");
        jobLauncher.run(priceParserJob, new JobParametersBuilder().addDate("launchDate", new Date()).toJobParameters());
    }

    @Scheduled(initialDelay = 1000 * 3, fixedDelayString = "${app.ps5finder.fixedDelay}")
    public void performPs5Finder() throws Exception {
        log.info("Starting PS5 Finder");
        jobLauncher.run(ps5FinderJob, new JobParametersBuilder().addDate("launchDate", new Date()).toJobParameters());
    }


}
