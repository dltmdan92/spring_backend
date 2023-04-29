package com.seungmoo.backend.batch.jobs.newyearvacation;

import com.seungmoo.backend.batch.BatchApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Disabled
@SpringBatchTest
@SpringBootTest(classes = BatchApplication.class)
@TestPropertySource(properties = "spring.batch.job.names="+NewYearVacationTemplateInitiateJob.JOB_NAME)
class NewYearVacationTemplateInitiateJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @DisplayName("배치 실행")
    void runBatch() throws Exception {
        jobLauncherTestUtils.launchJob();
    }

}