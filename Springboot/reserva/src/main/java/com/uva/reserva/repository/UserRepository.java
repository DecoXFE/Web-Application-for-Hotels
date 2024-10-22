package com.uva.reserva.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uva.reserva.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

}
