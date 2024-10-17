package com.uva.reserva.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uva.reserva.exception.HotelException;
import com.uva.reserva.model.Hotel;
import com.uva.reserva.model.Room;
import com.uva.reserva.repository.HotelRepository;
import com.uva.reserva.repository.RoomRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/RoomBooking/hotels")
public class HotelController {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    HotelController(HotelRepository hr, RoomRepository rr) {
        this.hotelRepository = hr;
        this.roomRepository = rr;
    }

    // Agrega un hotel a la base de datoas con sus habitaciones y reservas
    @PostMapping()
    public void createHotel(@RequestBody Hotel hotel) {
        try {
            if (hotel.getRoomCollection() != null) {
                hotel.getRoomCollection().forEach(room -> {
                    room.setHotelId(hotel);
                    if (room.getBookingCollection() != null) {
                        room.getBookingCollection().forEach(booking -> {
                            booking.setRoomId(room);
                        });
                    }
                });
            }
            hotelRepository.save(hotel);
        } catch (Exception e) {
            throw new HotelException("Error al crear nuevo hotel");
        }
    }

    // Elimina el hotel con dicha id de la base de datos
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable Integer id) {
        hotelRepository.deleteById(id);
    }

    // Consigue todos los hoteles de la base de datos
    @GetMapping()
    public List<Hotel> getHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels;
    }

    // Consigue los datos de un hotel en específico
    @GetMapping("/{id}")
    public Optional<Hotel> getHotelById(@PathVariable Integer id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return hotel;
    }

    // Devuelve los detalles de la habitación con el ID especificado.
    @GetMapping("/{idh}/rooms/{idr}")
    public Optional<Room> getRoomById(@PathVariable Integer idh, @PathVariable Integer idr) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(idh);
        Optional<Room> room;
        room = roomRepository.findByIdAndHotelId(idr,optionalHotel);
        return room;

    }

    // Devuelve la lista de las habitaciones disponibles de un hotel, en general,
    // y en una fechas dadas (con query en la URI).
    @GetMapping("/{id}/rooms")
    public List<Room> getAvailableRooms(@PathVariable Integer id,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        List<Room> rooms;
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (startDate != null && endDate != null) {
            try {
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);
                rooms = roomRepository.findAvailableRoomsInDateRangeByHotelId(id, start, end);
            } catch (Exception e) {
                throw new HotelException("Formato de fechas incorrectas");
            }

        } else {
            rooms = roomRepository.findByHotelIdAndAvailableTrue(hotel);
        }
        return rooms;
    }

    // Modifica la disponibilidad de la habitación.
    @PatchMapping("/{id}/rooms")
    public void updateAvailability(@PathVariable Integer id, @RequestBody List<Room> updatedRooms) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        List<Room> existingRooms = roomRepository.findByHotelId(hotel);
            updatedRooms.forEach(updatedRoom -> {
                existingRooms.stream()
                        .filter(existingRoom -> existingRoom.getId() == updatedRoom.getId())
                        .findFirst()
                        .ifPresent(existingRoom -> {
                            existingRoom.setAvailable(updatedRoom.isAvailable());
                            roomRepository.save(existingRoom);
                        });
            });
    }

}
