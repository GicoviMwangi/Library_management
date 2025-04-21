package com.library.App.config;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    public boolean checkUsername(Authentication authentication , String username){

        if (authentication.getName().equals(username)) return true;

        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SUPER_ADMIN") ||
                        grantedAuthority.getAuthority().equals("ADMIN"));
    }
}
