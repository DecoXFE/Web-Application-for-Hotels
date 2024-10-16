package com.uva.reserva.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    
    @Basic(optional = false)
    private String streetKind;
    @Basic(optional = false)
    private String streetName;
    @Basic(optional = false)
    private int number;
    @Basic(optional = false)
    private String postCode;
    @Basic(optional = false)
    private String otherInfo;

    public Address(){

    }
    
    public Address(String streetKind, String streetName, int number, String postCode, String otherInfo){
        this.streetKind = streetKind;
        this.streetName = streetName;
        this.number = number;
        this.postCode = postCode;
        this.otherInfo = otherInfo;
    }

    public String getStreetKind() {
        return streetKind;
    }

    public void setStreetKind(String streetKind) {
        this.streetKind = streetKind;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}
