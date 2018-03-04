package edu.itu.cavabunga.core.services;

import edu.itu.cavabunga.core.factory.AuthenticationFactory;
import edu.itu.cavabunga.core.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl {
    @Autowired
    private AuthenticationFactory authenticationFactory;

    @Autowired
    private AuthenticationRepository authenticationRepository;


}
