package com.umaraliev.personapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "individuals", schema = "person")
@Data
@ToString
public class Individual {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String passportNumber;
    private String phoneNumber;
    @CreationTimestamp
    private LocalDateTime verifiedAt;
    @CreationTimestamp
    private LocalDateTime archivedAt;
    private String status;
}