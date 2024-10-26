package com.uva.reserva.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NamedQueries({
        @NamedQuery(name = "Room.findAvailableRoomsInDateRangeByHotelId", query = "SELECT R FROM Room R WHERE R.hotelId.id = ?1 AND R.available = true AND NOT EXISTS(SELECT B FROM Booking B WHERE B.roomId = R AND B.startDate <= ?3 AND B.endDate >= ?2)")
})

// TODO: Revisar CascadeType
public class Room {
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;

    // ? unico para el hotel 
    @Basic(optional = false)
    @Column(nullable = false)
    private String roomNumber;

    @Basic(optional = false)
    @Column(nullable = false)
    private @Enumerated(EnumType.STRING) RoomType roomType;

    @Basic(optional = false)
    private boolean available;

    @OneToMany(mappedBy = "roomId", fetch = FetchType.EAGER)
    private List<Booking> bookingCollection;

    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonIgnore
    private Hotel hotelId;

    public Room() {

    }

    public Room(String roomNumber, RoomType roomType, boolean available, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.available = available;
        this.hotelId = hotel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public void setBookingCollection(List<Booking> bookingCollection) {
        this.bookingCollection = bookingCollection;
    }

    public Hotel getHotelId() {
        return hotelId;
    }

    public void setHotelId(Hotel hotelId) {
        this.hotelId = hotelId;
    }
}
