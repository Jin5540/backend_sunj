package com.backend.backendleeyejin.backend.repository;

import com.backend.backendleeyejin.backend.repository.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {
    Optional<Data> findByIndex (long userIndex);
    boolean existsByIndex (long userIndex);
}
