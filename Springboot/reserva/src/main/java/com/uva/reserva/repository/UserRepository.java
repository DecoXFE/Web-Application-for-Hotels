package com.uva.reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uva.reserva.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
