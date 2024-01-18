package com.pado.c3editions.app.editions.auth.repository;

import com.pado.c3editions.app.editions.auth.users.Notifications;
import com.pado.c3editions.app.editions.auth.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    List<Notifications> findByUser(Users user);

    Optional<Notifications> findByIdAndUser(Long id, Users user);
}