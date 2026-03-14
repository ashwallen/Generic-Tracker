package com.ash.GenericTracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parameters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parameter {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "bucket_id", nullable = false)
    private Bucket bucketId; //JPA stores only bucket id here.
    private String parameterName;
    private String dataType; // Time, Number, Text
    private Boolean isActive = true;
    private int parameterOrder;
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
