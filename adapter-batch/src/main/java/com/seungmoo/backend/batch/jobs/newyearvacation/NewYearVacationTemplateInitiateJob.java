package com.seungmoo.backend.batch.jobs.newyearvacation;

import com.seungmoo.backend.batch.jobs.newyearvacation.processor.ProcessFilterTargetUsers;
import com.seungmoo.backend.batch.jobs.newyearvacation.reader.ReadUsersReader;
import com.seungmoo.backend.batch.jobs.newyearvacation.writer.NewYearVacationTemplateWriter;
import com.seungmoo.backend.user.UserService;
import com.seungmoo.backend.user.dtos.UserDTO;
import com.seungmoo.backend.vacation.VacationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = NewYearVacationTemplateInitiateJob.JOB_NAME)
@RequiredArgsConstructor
public class NewYearVacationTemplateInitiateJob {

    public static final String JOB_NAME = "NEW_YEAR_VACATION_TEMPLATE_INITIATE_JOB";

    private static final String CREATE_NEW_YEAR_VACATION_TEMPLATE_STEP = "CREATE_NEW_YEAR_VACATION_TEMPLATE_STEP";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final int CHUNK_SIZE = 250;

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(createNewYearVacationTemplateStep())
                .build();

    }

    @Bean
    @JobScope
    Step createNewYearVacationTemplateStep() {
        return stepBuilderFactory.get(NewYearVacationTemplateInitiateJob.CREATE_NEW_YEAR_VACATION_TEMPLATE_STEP)
                .<UserDTO, Long>chunk(CHUNK_SIZE)
                .reader(readUsers(null))
                .processor(filterTargetUsers())
                .writer(writeNewYearVacationTemplate(null))
                .build();
    }

    @Bean
    @StepScope
    ItemReader<UserDTO> readUsers(UserService userService) {
        return new ReadUsersReader(userService, CHUNK_SIZE);
    }

    @Bean
    @StepScope
    ItemProcessor<UserDTO, Long> filterTargetUsers() {
        return new ProcessFilterTargetUsers();
    }

    @Bean
    @StepScope
    ItemWriter<Long> writeNewYearVacationTemplate(VacationService vacationService) {
        return new NewYearVacationTemplateWriter(vacationService);
    }
}
