package com.umaraliev.personapi.model;

import com.umaraliev.personapi.converter.ConverterHistoryJSONB;
import com.umaraliev.personapi.dto.IndividualDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_history", schema = "person")
@Data
public class UserHistory {
    @Id
    @GeneratedValue
    private UUID id;

    @CreationTimestamp
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String userType;
    private String reason;
    private String comment;

    @Convert(converter = ConverterHistoryJSONB.class)
    @Column(name = "changed_values", columnDefinition = "jsonb")
    private IndividualDTO changedValues;

}