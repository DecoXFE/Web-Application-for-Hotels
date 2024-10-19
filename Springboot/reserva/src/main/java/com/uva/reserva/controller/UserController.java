package com.uva.reserva.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uva.reserva.exception.UserException;
import com.uva.reserva.model.User;
import com.uva.reserva.model.UserStatus;
import com.uva.reserva.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/RoomBooking/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
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
            throw new UserException("Error al crear el nuevo usuario.");
        }
    }

    // Devuelve los detalles del usuario con el ID especificado.
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUserById(@PathVariable Integer id) {
        return repository.findById(id);
    }

    // Modifica los datos de un usuario (nombre, email).
    @PutMapping("/{id}")
    public void putUser(@RequestBody User userDetails, @PathVariable Integer id) {
        Optional<User> existingUser = repository.findById(id);
        User baseUser = existingUser.get();
        baseUser.setName(userDetails.getName());
        baseUser.setEmail(userDetails.getEmail());
        repository.save(baseUser);
    }

    // Elimina un usuario y sus reservas asociadas.
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    //Devuelve una lista con las reservas activas
    private long getActiveBookings(User user) {
        return user.getBookingCollection()
                .stream()
                .filter(booking -> booking.getStartDate().isBefore(LocalDate.now()) &&
                        booking.getEndDate().isAfter(LocalDate.now()))
                .count();

    }

    // Modifica el estado de un usuario (status) de forma consistente con sus
    // reservas.
    @PatchMapping("/{id}")
    public void patchUser(@RequestBody User userDetails, @PathVariable Integer id) {
        Optional<User> existingUser = repository.findById(id);
        User baseUser = existingUser.get();
        switch (userDetails.getStatus()) {
            case NOBOOKINGS:
                if (baseUser.getBookingCollection().isEmpty()) {
                    baseUser.setStatus(UserStatus.NOBOOKINGS);
                    repository.save(baseUser);
                } else {
                    throw new UserException("Existen reservas para este usuario.");
                }
                break;
            case WITHACTIVEBOOKINGS:
                if (getActiveBookings(baseUser) > 0) {
                    baseUser.setStatus(UserStatus.WITHACTIVEBOOKINGS);
                    repository.save(baseUser);
                } else {
                    throw new UserException("No existen reservas activas en este momento para este usuario.");
                }
                break;
            case WITHINACTIVEBOOKINGS:
                if (getActiveBookings(baseUser) == 0 && !baseUser.getBookingCollection().isEmpty()) {
                    baseUser.setStatus(UserStatus.WITHINACTIVEBOOKINGS);
                    repository.save(baseUser);
                } else {
                    throw new UserException("No existen reservas inactivas para este usuario en este momento.");
                }
                break;
            default:
                break;
        }
    }
}