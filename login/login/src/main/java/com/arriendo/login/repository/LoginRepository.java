package com.arriendo.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arriendo.login.*;

@Repository
public interface LoginRepository extends JpaRepository <login, Long>{
    Optional<login> findByEmail(String email);

}