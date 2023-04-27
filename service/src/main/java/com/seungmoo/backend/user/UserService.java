package com.seungmoo.backend.user;

import com.seungmoo.backend.domain.aggregates.user.User;
import com.seungmoo.backend.domain.repositories.user.UserRepository;
import com.seungmoo.backend.exceptions.UserEmailAlreadyExistsException;
import com.seungmoo.backend.user.dtos.UserDTO;
import com.seungmoo.backend.user.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());

        if (existingUser.isPresent()) {
            throw new UserEmailAlreadyExistsException(userDTO.getEmail());
        }

        userRepository.save(userMapper.toEntity(userDTO, passwordEncoder));

        // TODO vacationtemplate 생성 (휴가 15개 기본 셋팅)
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> getUser(String email, String flatPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(flatPassword, user.getPassword()))
                .map(userMapper::toDTO);
    }

}
