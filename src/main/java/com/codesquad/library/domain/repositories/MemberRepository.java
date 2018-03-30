package com.codesquad.library.domain.repositories;

import com.codesquad.library.domain.authentication.Member;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {

    Optional<Member> findByLoginEmail(String loginEmail);
}
