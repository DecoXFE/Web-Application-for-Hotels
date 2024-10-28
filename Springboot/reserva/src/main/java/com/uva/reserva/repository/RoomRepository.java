package com.uva.reserva.repository;


import java.util.Optional;
import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uva.reserva.model.Hotel;
import com.uva.reserva.model.Room;
import com.uva.reserva.model.RoomType;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByHotelIdAndAvailableTrue(Optional<Hotel> hotelId);
    List<Room> findAvailableRoomsInDateRangeByHotelId(Integer id, LocalDate start, LocalDate end);
    List<Room> findByHotelId(Optional<Hotel> hotel);
    Optional<Room> findByIdAndHotelId( Integer id, Optional<Hotel> hotel);
    List<Room> findByRoomType(RoomType roomType);
}