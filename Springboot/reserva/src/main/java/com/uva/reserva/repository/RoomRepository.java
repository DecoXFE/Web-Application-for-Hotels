package com.uva.reserva.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uva.reserva.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findRoomByHotelId(Integer idh, Integer idr);
}