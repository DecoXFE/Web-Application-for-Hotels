package com.uva.reserva.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

// TODO: Revisar CascadeType
public class User {
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(unique = true)
    private String email;

    // ! NO NECESARIO PARA PRIMERA ENTREGA
    @Basic(optional = true)
    private String password;

    @Basic(optional = false)
    @Column(nullable = false)
    private @Enumerated(EnumType.STRING) UserStatus status;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<Booking> bookingCollection;

    public User() {

    }

    public User(String name, String email, UserStatus status, String password) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public void setBookingCollection(List<Booking> bookingCollection) {
        this.bookingCollection = bookingCollection;
    }
}
