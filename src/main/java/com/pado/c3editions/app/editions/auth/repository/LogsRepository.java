package com.pado.c3editions.app.editions.auth.repository;

import com.pado.c3editions.app.editions.auth.logs.Logs;
import com.pado.c3editions.app.editions.auth.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogsRepository extends JpaRepository<Logs, Long> {
    List<Logs> findByUser(Users user);
}