package com.transportadora.user.repository;

import com.transportadora.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    @Query("SELECT u from User u JOIN FETCH u.roles where username = :username ")
    User findByUsernameFetchRoles(@Param("username") String username);
}
