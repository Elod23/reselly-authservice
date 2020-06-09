package com.reselly.authorizationservice.service;

import com.reselly.authorizationservice.entity.Credentials;
import com.reselly.authorizationservice.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    private CredentialRepository credentialRepository;

    @Autowired
    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public Credentials persistNewCredentials(Credentials credentials){
        return credentialRepository.save(credentials);
    }

    public Credentials getCredentialsAfterEmail(String email){
        return credentialRepository.findByEmail(email);
    }


}
