package com.seungmoo.backend.user;

import com.seungmoo.backend.domain.aggregates.user.User;
import com.seungmoo.backend.domain.repositories.user.UserRepository;
import com.seungmoo.backend.exceptions.UserEmailAlreadyExistsException;
import com.seungmoo.backend.user.dtos.UserDTO;
import com.seungmoo.backend.user.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> getUser(String email, String flatPassword) {
        return userRepository.findByEmailAndPassword(email, passwordEncoder.encode(flatPassword))
                .map(userMapper::toDTO);
    }

}
