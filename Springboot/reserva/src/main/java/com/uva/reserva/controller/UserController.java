package com.uva.reserva.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uva.reserva.exception.UserException;
import com.uva.reserva.model.User;
import com.uva.reserva.model.UserStatus;
import com.uva.reserva.repository.UserRepository;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/RoomBooking/users")
public class UserController {
    
    private final UserRepository repository;

    public UserController(UserRepository repository){
        this.repository = repository;
    }

    // Devuelve la lista de usuarios registrados
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    // Registra un nuevo usuario con estado sin reservas
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void newUser(@RequestBody User newUser) {
        try {
            newUser.setStatus(UserStatus.NOBOOKINGS);
            repository.save(newUser);
        } catch (Exception e) {
            throw new UserException("Error al crear el nuevo registro.");
        }
    }
}
