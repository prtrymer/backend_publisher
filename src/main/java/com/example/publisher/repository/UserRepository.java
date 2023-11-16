package com.example.publisher.repository;

import com.example.publisher.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByEmail(String email);
    UserEntity findUserEntitiesByName(String name);

    UserEntity findUserEntitiesBySurname(String surname);

    Boolean existsUserEntityByEmail(String email);

}
