package com.appdeveloperblog.app.ws.io.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="addresses")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 8796859580037297607L;

    @Id
    @GeneratedValue
    private long id;

    @Column(length=30, nullable = false)
    private String addressId;

    @Column(length=15, nullable = false)
    private String type;
    @Column(length=30, nullable = false)
    private String city;
    @Column(length=30, nullable = false)
    private String country;
    @Column(length=60)
    private String streetName;
    @Column(length=10, nullable = false)
    private String pinCode;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="users_id")
    private UserEntity userDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public UserEntity getUserEntity() {
        return userDetails;
    }

    public void setUserEntity(UserEntity userDetails) {
        this.userDetails = userDetails;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}
