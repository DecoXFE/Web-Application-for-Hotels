package com.uva.reserva.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/RoomBooking/hotels")
public class HotelController {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    HotelController(HotelRepository hr, RoomRepository rr) {
        this.hotelRepository = hr;
        this.roomRepository = rr;
    }

    /*
    *    Adds an hotel to the database with rooms
    */ 
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
            throw new HotelException("Error creating the hotel");
        }
    }

    /*
    * Deletes the hotel by id
    */
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable Integer id) {
        hotelRepository.deleteById(id);
    }

    /*
     * Returns all hotels from the database
     */
    @GetMapping()
    public List<Hotel> getHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels;
    }

    /*
     * Returns the details of a specific hotel
     */
    @GetMapping("/{id}")
    public Optional<Hotel> getHotelById(@PathVariable Integer id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return hotel;
    }

    /*
     * Returns the details of a specific room
     */
    @GetMapping("/{idh}/rooms/{idr}")
    public Optional<Room> getRoomById(@PathVariable Integer idh, @PathVariable Integer idr) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(idh);
        Optional<Room> room;
        room = roomRepository.findByIdAndHotelId(idr,optionalHotel);
        return room;

    }

    /*
     * Returns the available rooms, in general, and within a date range (QUERY URI)
     */
    @GetMapping("/{id}/rooms")
    public List<Room> getAvailableRooms(@PathVariable Integer id,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = true) boolean available) {
        List<Room> rooms;
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (startDate != null && endDate != null) {
            try {
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);
                rooms = roomRepository.findAvailableRoomsInDateRangeByHotelId(id, start, end);
            }
             catch (Exception e) {
                throw new HotelException("Incorrect date format");
            }
        } else if(available){
            rooms = roomRepository.findByHotelIdAndAvailableTrue(hotel);
        } else{
            rooms = roomRepository.findByHotelId(hotel);
        }
        return rooms;
    }

    /*
     * Changes the availability of a room
     */
    // ? Comprobación de HotelId
    @PatchMapping("/{idh}/rooms/{idr}")
    public void updateAvailability(@PathVariable Integer idh, @PathVariable Integer idr
    ) {
        try{
            Optional<Room> optionalRoom = roomRepository.findById(idr);
            Room room = optionalRoom.get();

            room.setAvailable(!room.isAvailable());
            roomRepository.save(room);
        }
        catch(Exception e){
            throw new HotelException("The room doesn't exist");
        }

    }

}
