package com.inventory.todoproject.infraestructure.repositories;

import com.inventory.todoproject.application.port.out.UserRepositoryPort;
import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.infraestructure.entities.UserEntity;
import com.inventory.todoproject.infraestructure.jparepository.JpaUserRepository;
import com.inventory.todoproject.infraestructure.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepositoryPort {
    private final UserMapper mapper;
    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(UserMapper mapper, JpaUserRepository jpaUserRepository) {
        this.mapper = mapper;
        this.jpaUserRepository = jpaUserRepository;
    }


    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity saved = jpaUserRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaUserRepository.existsById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return jpaUserRepository.findByUsername(userName).map(mapper::toDomain);
    }
}
