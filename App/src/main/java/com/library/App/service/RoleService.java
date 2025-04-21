package com.library.App.service;

import com.library.App.model.Role;
import com.library.App.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repository;

    public Object saveRole(Role role) {
        return repository.save(role);
    }
}
