package com.library.App.service;

import com.library.App.model.Users;
import com.library.App.model.UserPrincipal;
import com.library.App.repo.LibraryUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    private final LibraryUserRepository userRepository;


    @Autowired
    public MyUserDetailsService(LibraryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("USERNAME NOT FOUND"));

        if (user == null) throw new UsernameNotFoundException("USER NOT FOUND");

        return new UserPrincipal(user);
    }
}
