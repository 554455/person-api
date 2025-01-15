package com.umaraliev.personapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "person")
@Data
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String secretKey;
    private String email;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String firstName;
    private String lastName;
    private Boolean filled;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}