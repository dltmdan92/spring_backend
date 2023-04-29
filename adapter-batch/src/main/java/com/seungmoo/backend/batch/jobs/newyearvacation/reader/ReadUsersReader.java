package com.seungmoo.backend.batch.jobs.newyearvacation.reader;

import com.seungmoo.backend.user.UserService;
import com.seungmoo.backend.user.dtos.UserDTO;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.data.domain.PageRequest;

public class ReadUsersReader extends AbstractPagingItemReader<UserDTO> {
    private final UserService userService;

    public ReadUsersReader(UserService userService, int pageSize) {
        this.userService = userService;
        super.setPageSize(pageSize);
    }

    @Override
    protected void doReadPage() {
        super.results = userService.getUsers(PageRequest.of(super.getPage(), super.getPageSize()));
    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }
}
