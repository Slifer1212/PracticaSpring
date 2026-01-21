package com.inventory.todoproject.infrastructure.adapters.out.persistence.repositories;

import com.inventory.todoproject.application.ports.out.UserRepositoryPort;
import com.inventory.todoproject.domain.entities.User;
import com.inventory.todoproject.domain.pagination.Page;
import com.inventory.todoproject.domain.pagination.PageRequest;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.entities.UserEntity;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.jpa.JpaUserRepository;
import com.inventory.todoproject.infrastructure.adapters.out.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(entity);
        return userMapper.toDomain(savedEntity);    }

    @Override
    public Optional<User> findById(Long userId) {
        return jpaUserRepository.findById(userId).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String userName) {
        return jpaUserRepository.findByUsername(userName).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(userMapper::toDomain);

    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream().map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaUserRepository.existsById(id);
    }

    @Override
    public Page<User> findAll(PageRequest pageRequest) {
        return null;
    }
}
