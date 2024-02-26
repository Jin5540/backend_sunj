package com.backend.backendleeyejin.backend.repository;

import com.backend.backendleeyejin.backend.repository.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
}
