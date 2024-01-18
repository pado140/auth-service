package com.pado.c3editions.app.editions.auth.repository;

import com.pado.c3editions.app.editions.auth.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);
}