package com.umaraliev.personapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_history", schema = "person")
@Data
public class UserHistory {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String userType;
    private String reason;
    private String comment;

    @Column(name = "changed_values", columnDefinition = "jsonb")
    private String changedValues;

}