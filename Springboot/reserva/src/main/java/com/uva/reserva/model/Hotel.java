package com.uva.reserva.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id")
@NamedQueries({
    @NamedQuery(
        name = "Hotel.getAvailableRoomCollection",
        query = "SELECT R FROM Room R WHERE R.hotelId.id = ?1 AND R.available = true"
    ),
    @NamedQuery(
        name = "Hotel.findAvailableRoomsInDateRange",
        query = "SELECT R FROM Room R WHERE R.hotelId.id = ?1 AND R.available = true AND NOT EXISTS(SELECT B FROM Booking B WHERE B.roomId = R AND B.startDate <= ?3 AND B.endDate >= ?2)"
    )
})
public class Hotel {
    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @Basic(optional = false)
    private String name;
    
    @Basic(optional = false)
    private Address address;

    @OneToMany(mappedBy = "hotelId", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Room> roomCollection;

    public Hotel(){

    }

    public Hotel(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public long getId() {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Room> getRoomCollection() {
        return roomCollection;
    }

    public void setRoomCollection(List<Room> roomCollection) {
        this.roomCollection = roomCollection;
    }
}
