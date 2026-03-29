package com.ash.GenericTracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "entry_rows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryRow {

    @Id
    @GeneratedValue
    private UUID id;

    // Many rows belong to one entry
    @ManyToOne
    @JoinColumn(name = "entry_id", nullable = false)
    private Entry entry;

    // Helps maintain order in UI (row 1, row 2, etc.)
    private Integer rowIndex;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
