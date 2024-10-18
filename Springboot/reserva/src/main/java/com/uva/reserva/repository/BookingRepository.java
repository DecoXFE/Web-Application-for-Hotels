package com.uva.reserva.repository;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.uva.reserva.model.Booking;
import com.uva.reserva.model.Room;


public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByRoomId(Optional<Room> roomId);
    List<Booking> findByRoomIdAndStartDateBeforeAndEndDateAfter(Room roomId, LocalDate endDate, LocalDate startDate);
    List<Booking> findByStartDateAfterAndEndDateBefore(LocalDate startDate, LocalDate endDate);
    List<Booking> findByRoomIdAndStartDateAfterAndEndDateBefore(Optional<Room> roomId, LocalDate startDate, LocalDate endDate);
}
