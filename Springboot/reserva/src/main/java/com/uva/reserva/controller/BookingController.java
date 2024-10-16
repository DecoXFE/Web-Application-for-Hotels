package com.uva.reserva.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/RoomBooking/bookings")
public class BookingController {
    
    @GetMapping()
    public String getMethodName() {
        return "Hola bookings";
    }
}
