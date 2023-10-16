package com.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.entity.CusEntity;

@Repository
public interface CusRepository extends JpaRepository<CusEntity, String> {
	Optional<CusEntity> findByMail(String mail);
    boolean existsByMail(String mail);
}
