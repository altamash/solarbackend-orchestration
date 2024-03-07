package com.orchware.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_log_id", length = 10)
    private Long activityLogId;
    @Column(name = "source_type", length = 6)
    private String sourceType; //DEVICE, AGENT
    @Column(name = "deviceId", length = 15)
    private String deviceId;
    @Column(name = "activity_type", length = 5)
    private String activityType; //COMMS
    @Column(name = "status", length = 15)
    private String status;
    @Column(name = "agentId", length = 15)
    private String agentId;
    @Column(name = "agentRef", length = 15)
    private String agentRef;
    @Column(name = "log_result", length = 255)
    private String log_result;
    @Column(name = "err_message", length = 255)
    private String err_message;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
