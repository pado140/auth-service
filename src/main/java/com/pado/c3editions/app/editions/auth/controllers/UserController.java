package com.pado.c3editions.app.editions.auth.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.c3editions.app.editions.auth.client.EmployeClient;
import com.pado.c3editions.app.editions.auth.configuration.JWTHelper;
import com.pado.c3editions.app.editions.auth.dtos.*;
import com.pado.c3editions.app.editions.auth.logs.Logs;
import com.pado.c3editions.app.editions.auth.logs.LogsDto;
import com.pado.c3editions.app.editions.auth.security.LoginRequest;
import com.pado.c3editions.app.editions.auth.security.UserApp;
import com.pado.c3editions.app.editions.auth.security.UserAppService;
import com.pado.c3editions.app.editions.auth.services.NotificationService;
import com.pado.c3editions.app.editions.auth.services.UserService;
import com.pado.c3editions.app.editions.auth.users.Notifications;
import com.pado.c3editions.app.editions.auth.users.Roles;
import com.pado.c3editions.app.editions.auth.users.Users;
import com.pado.c3editions.app.editions.auth.utils.MapperUtils;
import com.pado.c3editions.app.editions.auth.utils.Reponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAppService userappService;

    @Autowired
    private EmployeClient employeClient;

    @Autowired
    private MapperUtils mapperUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("users")
//    @ApiOperation(value = "Liste des utilisateurs",notes = "Liste des utilisateurs")
    public Reponse<List<UsersDto>> listUser(HttpServletRequest request){
        List<UsersDto> users=mapperUtils.asDTOList(userService.findAll(),UsersDto.class);
        
        return Reponse.<List<UsersDto>>builder()
                .message("List des utilisateurs loaded")
                .timestamp(LocalDateTime.now())
                .statuscode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .path(request.getServletPath())
                .build();
    }

    @GetMapping("public/users")
//    @ApiOperation(value = "Liste des utilisateurs",notes = "Liste des utilisateurs")
    public List<UsersDto> listUser(){
        List<UsersDto> users=mapperUtils.asDTOList(userService.findAll(),UsersDto.class);

        return users;
    }

    @GetMapping("users/roles")
//    @ApiOperation(value = "Liste des utilisateurs",notes = "Liste des utilisateurs")
    public Reponse<List<RolesDto>> listroles(HttpServletRequest request){
        List<RolesDto> roles=mapperUtils.asDTOList(userService.findRoles(),RolesDto.class);

        return Reponse.<List<RolesDto>>builder()
                .data(Map.of("roles",roles))
                .message("List des roles loaded")
                .timestamp(LocalDateTime.now())
                .statuscode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .path(request.getServletPath())
                .build();
    }

    @GetMapping("public/roles")
//    @ApiOperation(value = "Liste des utilisateurs",notes = "Liste des utilisateurs")
    public List<RolesDto> listroles(){
        List<RolesDto> roles=mapperUtils.asDTOList(userService.findRoles(),RolesDto.class);

        return roles;
    }


    @GetMapping("users/{user_id}")
//    @ApiOperation(value = "",notes = "Selectionner un user")
    public Reponse<Object> get(@PathVariable("user_id") long user_id, HttpServletRequest request){
        Users user=userService.findById(user_id).get();
        user.setEmploye(employeClient.getbyuser(user_id));
        return Reponse
                .builder()
                .data(Map.of("user",mapperUtils.asDTO(user, UsersDto.class)))
                .message("User loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }


    @GetMapping("public/get/{user_id}")
//    @ApiOperation(value = "",notes = "Selectionner un user")
    public UsersDto getu(@PathVariable("user_id") long user_id){
        Users user=userService.findById(user_id).get();
        return mapperUtils.asDTO(user, UsersDto.class);
    }

    @GetMapping("public/getwe/{user_id}")
//    @ApiOperation(value = "",notes = "Selectionner un user")
    public UsersDto getwe(@PathVariable("user_id") long user_id){
        Users user=userService.findById(user_id).get();
        user.setEmploye(employeClient.getbyuser(user_id));
        return mapperUtils.asDTO(user, UsersDto.class);
    }

    @GetMapping("public/username/{username}")
//    @ApiOperation(value = "",notes = "Selectionner un user")
    public UsersDto get(@PathVariable("username") String username){
        return mapperUtils.asDTO(userService.getByUsername(username), UsersDto.class);
    }

    @GetMapping("public/usernamewe/{username}")
//    @ApiOperation(value = "",notes = "Selectionner un user")
    public UsersDto getuwe(@PathVariable("username") String username){
        Users user=userService.getByUsername(username);
        user.setEmploye(employeClient.getbyuser(user.getId()));
        return mapperUtils.asDTO(user, UsersDto.class);
    }

    @PostMapping("users")
//    @ApiOperation(value = "Enregistrer un utilisateur",notes = "Enregistrer un utilisateur")
    public Reponse<Object> save(@RequestBody @Valid Users user){
        user.setEmployeId(user.getEmploye().getId());
        return Reponse.builder()
                .data(Map.of("user",mapperUtils.asDTO(userService.save(user),UsersDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message("Nouveau user créé")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("authenticate")
//    @ApiOperation(value = "Enregistrer un utilisateur",notes = "Enregistrer un utilisateur")
    public Reponse<Object> save(@RequestBody @Valid LoginRequest user, HttpServletRequest request){
        UsernamePasswordAuthenticationToken usertoken=new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        var principal=authenticationManager.authenticate(usertoken);
        String token="";
        String refresh_token="";
        if(principal.isAuthenticated()){
            UserApp userlogged=(UserApp)principal.getPrincipal();
            userlogged.getUser().setEmploye(employeClient.getbyuser(userlogged.getUser().getId()));
           token= JWTHelper.generateToken(userlogged,request.getRequestURL().toString(),false);
           String photo=null;
           if(Objects.nonNull(userlogged.getUser().getEmploye()))
               photo=userlogged.getUser().getEmploye().getPhoto();


            refresh_token= JWTHelper.generateToken(userlogged,request.getRequestURL().toString(),true);
            return Reponse.builder()
                    .data(Map.of("token",TokenDto.builder().access_token(token)
                            .refresh_token(refresh_token)
                            .username(userlogged.getUsername()).photo(photo)
                            .user(mapperUtils.asDTO(userlogged.getUser(),UsersDto.class)).build()))
                    .statuscode(HttpStatus.CREATED.value())
                    .timestamp(LocalDateTime.now())
                    .path(request.getServletPath())
                    .message("Connection succes")
                    .status(HttpStatus.CREATED)
                    .build();
        }
//        var user=
        return Reponse.builder()
                .data(null)
                .statuscode(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .path(request.getServletPath())
                .message("CConnection echoue")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("users/{user_id}")
//    @ApiOperation(value = "Modifier un user",notes = "Modifier un user")
    public Reponse<Object> update(@RequestBody @Valid Users user,@PathVariable long user_id,HttpServletRequest request){

        return Reponse.builder()
                .data(Map.of("user",mapperUtils.asDTO(userService.update(user,user_id),UsersDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .message(String.format("Utilisateur %s vient d'etre modifie",user.getUsername()))
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("public/log")
    public LogsDto savelog(@RequestBody @Valid Logdto log) {
        Logs l=userService.saveLog(log);
        return mapperUtils.asDTO(l, LogsDto.class);
    }

    @GetMapping("refresh-token")
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorization=request.getHeader(HttpHeaders.AUTHORIZATION);
        if(Strings.isBlank(authorization)||!authorization.startsWith("Bearer ")){
            throw new RuntimeException("token is invalid");
        }

        System.out.println(authorization);
        try{
            String token=authorization.split(" ")[1];

            String username= JWTHelper.extractUsername(token);
            UserApp user= (UserApp) userappService.loadUserByUsername(username);
            String new_token=JWTHelper.generateToken(user,request.getRequestURL().toString(),false);
            String refresh_token=JWTHelper.generateToken(user,request.getRequestURL().toString(),true);

            response.setHeader("access_token",new_token);
            response.setHeader("refresh_token",refresh_token);
            response.addHeader("Authorization","Bearer "+new_token);

            new ObjectMapper().writeValue(response.getOutputStream(), TokenDto.builder().access_token(new_token).refresh_token(refresh_token).username(user.getUsername()).build());
        }catch ( Exception exception){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            new ObjectMapper().writeValue(response.getOutputStream(), Map.of("error_message", exception.getMessage()));
            exception.printStackTrace();

//            new ObjectMapper().writeValue(response.getOutputStream(), Map.of("error_message", exception.getMessage()));
        }
    }

    @PutMapping("{id}/change-password")
    public void changePassword(@RequestBody Users userdata, @PathVariable("id") long user_id,HttpServletRequest request, HttpServletResponse response) throws IOException {
        Users user=userService.findById(user_id).get();
        user.setPassword(passwordEncoder.encode(userdata.getPassword()));
        user.setChangepassword(false);
        user.setRecover(UUID.randomUUID().toString());

        if(Objects.nonNull(userService.update(user,user_id))) {
            String username = user.getUsername();
            UserApp userapp = (UserApp) userappService.loadUserByUsername(username);
            String new_token = JWTHelper.generateToken(userapp, request.getRequestURL().toString(), false);
            String refresh_token = JWTHelper.generateToken(userapp, request.getRequestURL().toString(), true);

            response.setHeader("access_token", new_token);
            response.setHeader("refresh_token", refresh_token);
            response.addHeader("Authorization", "Bearer " + new_token);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            new ObjectMapper().writeValue(response.getOutputStream(), TokenDto.builder().access_token(new_token).refresh_token(refresh_token).username(user.getUsername()).build());
        }else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            new ObjectMapper().writeValue(response.getOutputStream(),Map.of("Erreur","Erreur fatal"));
        }
    }

    @GetMapping("logged-user")
    public Object loggedInUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorization=request.getHeader(HttpHeaders.AUTHORIZATION);
        if(Strings.isBlank(authorization)||!authorization.startsWith("Bearer ")){
            throw new RuntimeException("token is invalid");
        }
        try{
            String token=authorization.split(" ")[1];

            String username= JWTHelper.extractUsername(token);
            UserApp ua= (UserApp) userappService.loadUserByUsername(username);
            ua.getUser().setEmploye(employeClient.getbyuser(ua.getUser().getId()));
            return mapperUtils.asDTO(ua.getUser(), UsersDto.class);
            //return user;//mapperUtils.asDTO(user.getUser(), ConnectedUserDto.class);
        }catch ( Exception exception){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            new ObjectMapper().writeValue(response.getOutputStream(), Map.of("error_message", exception.getMessage()));
        }
        return null;
    }

    @PostMapping("users/create")
//    @ApiOperation(value = "Enregistrer un utilisateur",notes = "Enregistrer un utilisateur")
    public Reponse<Object> save(@RequestBody userNew user, HttpServletRequest request){
        Users u=Users.builder().username(user.getUsername()).password("pass1234").build();
        Roles r=userService.getRole(user.getRole());
        if(Objects.nonNull(r)){
            u.setRole(r);
        }
//        Roles r=Roles.builder().name(role).bui
        return Reponse.builder()
                .data(Map.of("user",mapperUtils.asDTO(userService.save(u),UsersDto.class)))
                .statuscode(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .path(request.getServletPath())
                .message("Nouveau user créé")
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("public/create")
    public UsersDto save(@RequestBody userNew user){
        Users u=Users.builder().username(user.getUsername()).password("pass1234").build();
        u.setChangepassword(true);
        Roles r=userService.getRole(user.getRole());
        if(Objects.nonNull(r)){
            u.setRole(r);
        }
        return mapperUtils.asDTO(userService.save(u),UsersDto.class);
    }

    @GetMapping("notifications")
    public Reponse<Object> listNotification(HttpServletRequest request, Principal principal){
        Users user=userService.getByUsername(principal.getName());
        List<Notifications> notifications=notificationService.received(user);
        return Reponse
                .builder()
                .data(Map.of("notifications",mapperUtils.asDTOList(notifications, NotificationsDto.class)))
                .message("Liste des notifications loaded")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }


//
//    @GetMapping("profil")
//    public Reponse<Object> listLogs(HttpServletRequest request, Principal principal){
//        Users user=userService.getByUsername(principal.getName());
//        List<Logs> logs=logServices.findByUser(user);
//        List<Tasks> tasks=taskService.findByUser(user);
//        return Reponse
//                .builder()
//                .data(Map.of("logs",mapperUtils.asDTOList(logs, LogsDto.class),
//                        "tasks",mapperUtils.asDTOList(tasks, TasksDto.class)))
//                .message("Liste des taches et logs loaded")
//                .path(request.getServletPath())
//                .timestamp(LocalDateTime.now())
//                .status(HttpStatus.OK)
//                .statuscode(HttpStatus.OK.value())
//                .build();
//    }


    @PutMapping("notifications/{notif_id}")
    public Reponse<Object> updateNotification(@PathVariable("notif_id") Long notif_id, HttpServletRequest request, Principal principal){
        Notifications notification=notificationService.findById(notif_id).get();
        notification.setIsread(true);
        System.out.println("id="+notif_id);
        Notifications notifications=notificationService.update(notification,notif_id);
        return Reponse
                .builder()
                .data(Map.of("notification",mapperUtils.asDTO(notifications, NotificationsDto.class)))
                .message("Notification mis a jour")
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statuscode(HttpStatus.OK.value())
                .build();
    }

}
