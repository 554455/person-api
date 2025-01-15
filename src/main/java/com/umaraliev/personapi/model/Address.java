package com.umaraliev.personapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "addresses", schema = "person")
@Data
public class Address {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime created;
    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private String address;
    private String zipCode;
    private LocalDateTime archived;
    private String city;
    private String state;

}