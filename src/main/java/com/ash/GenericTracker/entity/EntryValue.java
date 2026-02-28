package com.ash.GenericTracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "entry_values")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryValue {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "entry_id", nullable = false)
    private Entry entry;

    @ManyToOne
    @JoinColumn(name = "parameter_id", nullable = false)
    private Parameter parameter;

    private String valueText;

    private Double valueNumber;

    private String valueTime; // store as string like "2h 30m" for simplicity

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
