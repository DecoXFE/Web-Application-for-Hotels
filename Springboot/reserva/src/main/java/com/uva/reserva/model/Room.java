package com.uva.reserva.model;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Room {
    @Id
    @GeneratedValue
    @Basic(optional = false)
    private long id;
    
    @Basic(optional = false)
    private String roomNumber;
    
    @Basic(optional = false)
    private @Enumerated(EnumType.STRING) RoomType roomType;
    
    @Basic(optional = false)
    private boolean available;

    @OneToMany(mappedBy = "roomId", fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Booking> bookingCollection;

    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
    private Hotel hotelId;

    public Room(){

    }

    public Room(String roomNumber, RoomType roomType, boolean available, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.available = available;
        this.hotelId = hotel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
