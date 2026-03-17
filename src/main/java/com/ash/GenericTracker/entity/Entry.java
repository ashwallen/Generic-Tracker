package com.ash.GenericTracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "entries",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bucket_id","entryDate"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Entry {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "bucket_id", nullable = false)
    private Bucket bucketId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
    private LocalDate entryDate;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updateAt = LocalDateTime.now();
    }
}
