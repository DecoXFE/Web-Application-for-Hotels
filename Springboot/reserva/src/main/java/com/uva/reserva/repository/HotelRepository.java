package com.uva.reserva.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uva.reserva.model.Hotel;
import com.uva.reserva.model.Room;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Room> getAvailableRoomCollection(Integer id);
    List<Room> findAvailableRoomsInDateRange(Integer id, LocalDate start, LocalDate end);
}