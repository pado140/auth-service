package com.pado.c3editions.app.editions.auth.repository;

import com.pado.c3editions.app.editions.auth.users.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query("select r from Roles r where r.name = ?1")
    Optional<Roles> findByName(String name);
}