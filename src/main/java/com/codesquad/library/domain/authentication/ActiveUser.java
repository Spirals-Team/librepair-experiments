package com.codesquad.library.domain.authentication;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Arrays;

@Getter
public class ActiveUser extends User {

    private static final String ROLE_PREFIX = "ROLE_";
    private final Member member;

    public ActiveUser(Member member) {
        super(member.getLoginEmail(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority(ROLE_PREFIX + member.getRoles().getRole())));
        this.member = member;
    }

    @Override
    public String getPassword() {
        return this.member.getPassword();
    }
}
