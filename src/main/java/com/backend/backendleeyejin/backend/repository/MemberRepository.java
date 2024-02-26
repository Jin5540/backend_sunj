package com.backend.backendleeyejin.backend.repository;

import com.backend.backendleeyejin.backend.repository.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserId(String userId);
    boolean existsByRegNo(String regNo);
    Optional<Member> findByUserId(String userId);

}
