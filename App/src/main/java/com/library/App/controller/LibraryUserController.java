package com.library.App.controller;


import com.library.App.model.Role;
import com.library.App.model.Users;
import com.library.App.service.LibraryUserService;
import com.library.App.service.RoleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class LibraryUserController {
    @Autowired
    private LibraryUserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user){
        //do a check for username present in the db
        return ResponseEntity.ok().body(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user){
        return ResponseEntity.ok().body(userService.validate(user));
    }


    public ResponseEntity<?> saveRole(Role role){
        return new ResponseEntity<>(roleService.saveRole(role), HttpStatus.OK);
    }

    public ResponseEntity addRoleToUser(RoleToUser user){
        userService.addRoleToUser(user.getUsername(),user.getRoleName());
        return ResponseEntity.ok().build();
    }

    public ResponseEntity assignRoleToUser(String username,String roleName){
        userService.addRoleToUser(username,roleName);
        return ResponseEntity.ok().build();
    }
}
@Data
class RoleToUser{
    private String username;
    private String roleName;
}