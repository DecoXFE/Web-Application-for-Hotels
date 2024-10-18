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
    // ? Debería roomId y userId pasarse por URI?
    @PostMapping()
    public void createBooking(@RequestBody Booking newBooking,
            @RequestParam Integer roomId,
            @RequestParam Integer userId) {
        try {

            if (newBooking.getStartDate().isAfter(newBooking.getEndDate())) {
                throw new BookingException("Las fechas de la reserva no tienen sentido");
            }

            Optional<User> userOptional = userRepository.findById(userId);
            Optional<Room> roomOptional = roomRepository.findById(roomId);

            if (userOptional.isPresent() && roomOptional.isPresent()) {
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
                    throw new BookingException("La habiación no está disponible en esa fecha");
                }
            }
        } catch (Exception e) {
            throw new BookingException("No se pudo añadir la reserva");
        }
    }

    /*
     * Devuelve la lista de las reservas en unas fechas y/o para una habitación
     * dada (con query en la URI).
     */
    // TODO: Agregar posibilidad para buscar por las 3 combinaciones
    @GetMapping()
    public List<Booking> findBookings(@RequestParam Integer roomId, 
    @RequestParam LocalDate startDate,
    @RequestParam LocalDate endDate) {
        Optional<Room> room = roomRepository.findById(roomId);
        List<Booking> bookings = bookingRepository.findByRoomId(room);
        return bookings;
    }

    // Elimina una reserva existente.
    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Integer id) {
        bookingRepository.deleteById(id);
    }
}
