package com.inventory.todoproject.application.Validator;

import com.inventory.todoproject.application.port.out.UserRepositoryPort;
import com.inventory.todoproject.domain.exception.EmailAlreadyExistsException;
import com.inventory.todoproject.domain.exception.UsernameAlreadyExistsException;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final UserRepositoryPort userRepository;

    public UserValidator(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUniqueEmail(String email, Long userId){
        userRepository.findByEmail(email).ifPresent(
                user -> {
                    if(!user.getId().equals(userId)){
                        throw  new EmailAlreadyExistsException(email);
                    }
                }
        );
    }

    public void validateUniqueUsername(String userName, Long userId){
        userRepository.findByUserName(userName).ifPresent(
                user -> {
                    if(!user.getId().equals(userId)){
                        throw  new UsernameAlreadyExistsException(userName);
                    }
                }
        );
    }
}
