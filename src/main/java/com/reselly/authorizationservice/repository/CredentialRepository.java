package com.reselly.authorizationservice.repository;

import com.reselly.authorizationservice.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credentials, Long> {
    public Credentials findByEmail(String email);
}
