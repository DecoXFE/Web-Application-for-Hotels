package com.uva.reserva.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uva.reserva.exception.UserException;
import com.uva.reserva.model.User;
import com.uva.reserva.model.UserStatus;
import com.uva.reserva.repository.UserRepository;

@RestController
@RequestMapping("/RoomBooking/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    /*
     * Returns all users
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    /*
     * Creates a new user
     */
    // ? USAR EMAIL VALIDATOR DE ANGULAR
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void newUser(@RequestBody User newUser) {
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(newUser.getEmail());
        if (!matcher.matches()) {
            throw new UserException("Incorrect email format");
        } 
        if(repository.findByEmail(newUser.getEmail()).isPresent()){
            throw new UserException("Email already exists");
        }
        newUser.setStatus(UserStatus.NOBOOKINGS);
        repository.save(newUser);
    }

    /*
     * Returns the details of a specific user
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUserById(@PathVariable Integer id) {
        return repository.findById(id);
    }

    /*
     * Changes the data of a user
     */
    @PutMapping("/{id}")
    public void putUser(@RequestBody User userDetails, @PathVariable Integer id) {
        Optional<User> existingUser = repository.findById(id);
        User baseUser = existingUser.get();
        baseUser.setName(userDetails.getName());
        baseUser.setEmail(userDetails.getEmail());
        repository.save(baseUser);
    }

    /*
     * Deletes a user and it's bookings
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    /*
     * Returns all active bookings
     */
    private long getActiveBookings(User user) {
        return user.getBookingCollection()
                .stream()
                .filter(booking -> booking.getStartDate().isBefore(LocalDate.now()) &&
                        booking.getEndDate().isAfter(LocalDate.now()))
                .count();

    }

    /*
     * Modify the state of a user consistently with its bookings.
     */
    // ! METODO TOTALMENTE MAL, HAY QUE CAMBIAR AL STATUS QUE LE CORRESPONDE DE MANERA AUTOMÁTICA
    // ? Preguntar como hacer que se actualice solo
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