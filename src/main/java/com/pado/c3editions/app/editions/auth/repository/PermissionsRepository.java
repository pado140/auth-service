package com.pado.c3editions.app.editions.auth.repository;

import com.pado.c3editions.app.editions.auth.users.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
}