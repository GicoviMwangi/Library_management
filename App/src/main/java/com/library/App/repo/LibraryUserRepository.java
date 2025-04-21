package com.library.App.repo;

import com.library.App.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryUserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String username);
}
