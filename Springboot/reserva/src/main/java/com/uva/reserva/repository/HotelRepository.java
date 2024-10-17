package com.uva.reserva.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.uva.reserva.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
}