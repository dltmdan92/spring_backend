package com.seungmoo.backend.batch.jobs.newyearvacation.processor;

import com.seungmoo.backend.user.dtos.UserDTO;
import org.springframework.batch.item.ItemProcessor;

public class ProcessFilterTargetUsers implements ItemProcessor<UserDTO, Long> {

    @Override
    public Long process(UserDTO user) {
        return user.getUserId();
    }
}
