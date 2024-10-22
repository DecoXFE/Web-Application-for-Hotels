package com.uva.reserva.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uva.reserva.exception.BookingException;
import com.uva.reserva.model.Booking;
import com.uva.reserva.model.Room;
import com.uva.reserva.model.RoomType;
import com.uva.reserva.model.User;
import com.uva.reserva.repository.BookingRepository;
import com.uva.reserva.repository.RoomRepository;
import com.uva.reserva.repository.UserRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/RoomBooking/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public BookingController(BookingRepository br,
            RoomRepository rr,
            UserRepository ur) {
        this.bookingRepository = br;
        this.roomRepository = rr;
        this.userRepository = ur;
    }

    // * Crea una nueva reserva de habitacion para un usuario.
    @PostMapping()
    public void createBooking(@RequestBody Booking newBooking,
            @RequestParam RoomType roomType,
            @RequestParam String userEmail) {

        if (newBooking.getStartDate().isAfter(newBooking.getEndDate())) {
            throw new BookingException("End date is before start date");
        }

        if(newBooking.getStartDate().isBefore(LocalDate.now())){
            throw new BookingException("Start date has already passed");
        }

        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        Optional<Room> roomOptional = roomRepository.findByRoomType(roomType);

        if (!userOptional.isPresent()) {
            throw new BookingException("Email doesn't exist");
        }

        if (!roomOptional.isPresent()) {
            throw new BookingException("No rooms available for the room type selected");
        }

        User user = userOptional.get();
        Room room = roomOptional.get();
        Integer overlappingBookings = bookingRepository
                .findByRoomIdAndStartDateBeforeAndEndDateAfter(room, newBooking.getEndDate(),
                        newBooking.getStartDate())
                .size();
        if (room.isAvailable() && overlappingBookings == 0) {
            newBooking.setRoomId(room);
            newBooking.setUserId(user);
            bookingRepository.save(newBooking);
        } else {
            throw new BookingException("There's no room available in that range");
        }
    }

    /*
     * Devuelve la lista de las reservas en unas fechas y/o para una habitación
     * dada (con query en la URI).
     */
    @GetMapping()
    public List<Booking> findBookings(@RequestParam(required = false) Integer roomId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        if (roomId != null && (startDate == null || endDate == null)) {
            Optional<Room> room = roomRepository.findById(roomId);
            List<Booking> bookings = bookingRepository.findByRoomId(room);
            return bookings;
        } else if (roomId == null && startDate != null && endDate != null) {
            List<Booking> bookings = bookingRepository.findByStartDateAfterAndEndDateBefore(startDate.plusDays(-1),
                    endDate.plusDays(1));
            return bookings;
        } else if (roomId != null && startDate != null && endDate != null) {
            Optional<Room> room = roomRepository.findById(roomId);
            List<Booking> bookings = bookingRepository.findByRoomIdAndStartDateAfterAndEndDateBefore(room,
                    startDate.plusDays(-1), endDate.plusDays(1));
            return bookings;

        } else
            throw new BookingException("Error en los argumentos");
    }

    // Elimina una reserva existente.
    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Integer id) {
        bookingRepository.deleteById(id);
    }

    // Devuelve los detalles de una reserva.
    @GetMapping("/{id}")
    public Optional<Booking> getBookingInfo(@PathVariable Integer id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking;
    }

}
