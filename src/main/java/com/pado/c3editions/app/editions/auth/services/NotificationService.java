package com.pado.c3editions.app.editions.auth.services;

import com.pado.c3editions.app.editions.auth.repository.NotificationsRepository;
import com.pado.c3editions.app.editions.auth.users.Notifications;
import com.pado.c3editions.app.editions.auth.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    public Notifications save(Notifications entity) {
        return notificationsRepository.save(entity);
    }

    public List<Notifications> save(List<Notifications> entities) {
        return null;
    }

    public boolean deleteById(Long id) {
        return true;
    }

    public Optional<Notifications> findById(Long id) {
        Notifications user=notificationsRepository.findById(id).get();
        return Optional.of(user);
    }

    public List<Notifications> findAll() {
        List<Notifications> users=notificationsRepository.findAll();
        return users;
    }

    public Notifications update(Notifications entity, Long id) {
        entity.setId(id);
        return notificationsRepository.save(entity);
    }

    public List<Notifications> received(Users user) {
        List<Notifications> users=notificationsRepository.findByUser(user);
        return users;
    }
}
