package com.uva.reserva.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Booking {
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate startDate;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate endDate;

    // ! Link
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnore
    private User userId;

    // ! Link
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnore
    private Room roomId;

    public Booking() {

    }

    public Booking(LocalDate startDate, LocalDate endDate, User user, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = user;
        this.roomId = room;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }
}
