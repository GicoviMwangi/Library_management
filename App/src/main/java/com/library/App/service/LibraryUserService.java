package com.library.App.service;

import com.library.App.model.Role;
import com.library.App.model.Users;
import com.library.App.repo.LibraryUserRepository;
import com.library.App.repo.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class LibraryUserService {
    @Autowired
    private LibraryUserRepository libraryUserRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private ApplicationContext context;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public Object register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> managedRoles = new HashSet<>();
        for (Role role : user.getRoles()){
            Role managedRole = roleRepository.findByRoleName(role.getRoleName()).orElseGet(() -> roleRepository.save(role));
            managedRoles.add(managedRole);
        }

        user.setRoles(managedRoles);
        return libraryUserRepository.save(user);
    }

    public String validate(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(user.getUsername());
            return jwtService.generateToken(userDetails);
        }
        throw new UsernameNotFoundException("INCORRECT USERNAME/PASSWORD");
    }

    public void addRoleToUser(String username, String roleName) {
        Users user = libraryUserRepository.findByUsername(username).orElseThrow(() ->new UsernameNotFoundException("USER NOT FOUND"));

        if (user == null) throw new UsernameNotFoundException("USER NOT FOUND");

        Role role = roleRepository.findByRoleName(roleName).orElseGet(() -> roleRepository.save(new Role(null,roleName)));
        user.getRoles().add(role);

    }
}
