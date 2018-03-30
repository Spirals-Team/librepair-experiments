package com.codesquad.library.security;

import com.codesquad.library.domain.authentication.ActiveUser;
import com.codesquad.library.domain.exceptions.security.NoAccountPresentException;
import com.codesquad.library.domain.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ActiveUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginEmail) throws NoAccountPresentException {
        return new ActiveUser(memberRepository.findByLoginEmail(loginEmail).orElseThrow(() -> new NoAccountPresentException("ID가 존재하지 않습니다.")));
    }
}
