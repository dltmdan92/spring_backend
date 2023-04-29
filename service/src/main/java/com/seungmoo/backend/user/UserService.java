package com.seungmoo.backend.user;

import com.seungmoo.backend.domain.aggregates.user.User;
import com.seungmoo.backend.domain.repositories.user.UserRepository;
import com.seungmoo.backend.user.exceptions.UserEmailAlreadyExistsException;
import com.seungmoo.backend.user.dtos.UserDTO;
import com.seungmoo.backend.user.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signUp(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());

        if (existingUser.isPresent()) {
            throw new UserEmailAlreadyExistsException(userDTO.getEmail());
        }

        return userRepository.save(userMapper.toEntity(userDTO, passwordEncoder));
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> getUser(String email, String flatPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(flatPassword, user.getPassword()))
                .map(userMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getUsers(Pageable pageable) {
        return userRepository.findAllUsers(pageable).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

}
