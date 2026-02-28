package com.ash.GenericTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parameters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "bucket_id", nullable = false)
    private Bucket bucketId;
    private String paramName;
    private String dataType; // Time, Number, Text
    private Boolean isActive = true;
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
