package com.umaraliev.personapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
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

    @Column(name = "changed_values", columnDefinition = "jsonb")
    private String changedValues;

    public UserHistory(User user, String userType, String reason, String comment, String changedValues) {
        this.user = user;
        this.userType = userType;
        this.reason = reason;
        this.comment = comment;
        this.changedValues = changedValues;
    }

    public UserHistory() {

    }
}
