package com.orchware.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Entity
@Table(name = "comm_schedule_tzs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommScheduleTz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", length = 10)
    private Long scheduleUd;
    @Column(name = "timezone", length = 40)
    private TimeZone timezone;
    @Column(name = "sunrise")
    private LocalDateTime sunrise;
    @Column(name = "sunset")
    private LocalDateTime sunset;
    @Column(name = "time_off_set", length = 25)
    private LocalDateTime timeoffset;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
