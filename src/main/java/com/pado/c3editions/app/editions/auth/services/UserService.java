package com.pado.c3editions.app.editions.auth.services;

import com.pado.c3editions.app.editions.auth.dtos.Logdto;
import com.pado.c3editions.app.editions.auth.exceptions.UserException;
import com.pado.c3editions.app.editions.auth.logs.Logs;
import com.pado.c3editions.app.editions.auth.repository.LogsRepository;
import com.pado.c3editions.app.editions.auth.repository.RolesRepository;
import com.pado.c3editions.app.editions.auth.repository.UsersRepository;
import com.pado.c3editions.app.editions.auth.users.Roles;
import com.pado.c3editions.app.editions.auth.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService  {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private LogsRepository logsRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users save(Users entity) {
        if(userRepository.existsByUsername(entity.getUsername())){
            throw new UserException("Ce nom d'utilisateur a deja ete prise", HttpStatus.NOT_FOUND);
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setRecover(UUID.randomUUID().toString());

        return userRepository.save(entity);
    }

    public List<Users> save(List<Users> entities) {
        return null;
    }

    public boolean deleteById(Long id) {
        if(!userRepository.existsById(id)){
            throw new UserException("Utilisateur non trouve", HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
        return true;
    }

    public Optional<Users> findById(Long id) {
        Users user=userRepository.findById(id).orElseThrow(() -> new UserException("Utilisateur non trouve",HttpStatus.NOT_FOUND));
        return Optional.of(user);
    }

    public List<Users> findAll() {
        List<Users> users=Optional.of(userRepository.findAll()).orElseThrow(()->new UserException("Aucun utilisateur trouve",HttpStatus.NOT_FOUND));
        return users;
    }

    public Page<Users> findAll(Pageable pageable) {
        return null;
    }

    public Users update(Users entity, Long id) {
        Users user=userRepository.findById(id).orElseThrow(() -> new UserException("Utilisateur non trouve",HttpStatus.NOT_FOUND));
        entity.setId(id);
        return userRepository.save(entity);
    }

    public Users getByUsername(String username) {
        Users user=userRepository.findByUsername(username).orElseThrow(() -> new UserException("Utilisateur non trouve",HttpStatus.NOT_FOUND));

        return user;
    }

    public Users setRole(Long id,Roles role){
        Users user=userRepository.findById(id).orElseThrow(() -> new UserException("Utilisateur non trouve",HttpStatus.NOT_FOUND));
        user.setRole(role);
        return userRepository.save(user);
    }

    public Logs saveLog(Logdto logs){
        Users us=getByUsername(logs.getUser());
        var l=Logs.builder()
                .action(logs.getAction())
                .claz(logs.getClaz())
                .desciption(logs.getMessage()).user(us);
        if(Objects.nonNull(logs.getPayload())) {
            if (!logs.getPayload().isEmpty()) {
                if (logs.getPayload().containsKey("livre"))
                    l.livre((Long) logs.getPayload().get("livre"));
                if (logs.getPayload().containsKey("projet"))
                    l.projet((Long) logs.getPayload().get("projet"));
            }
        }
        var log=l.build();
//        us.getLogs().add(log);
        log=logsRepository.save(log);
        return log;
    }

    public Roles getRole(String role){
        return rolesRepository.findByName(role).orElse(null);
    }

    public List<Roles> findRoles(){
        return rolesRepository.findAll();
    }
}
