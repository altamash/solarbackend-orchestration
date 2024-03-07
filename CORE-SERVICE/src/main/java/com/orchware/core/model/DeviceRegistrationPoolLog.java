package com.orchware.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "device_reg_pool_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRegistrationPoolLog {

    @Id
    @Column(name = "pool_log_id", length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long devicePoolLogId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "device_pool_id")
    private DeviceRegistrationPool deviceRegistrationPool;
    @Column(name = "agent_id", length = 6)
    private Long agentId;
    @Column(name = "status", length = 128) //TODO: DataType
    private String status;
    @Column(name = "description", length = 511)
    private String description;
    @Column(name = "date_time", length = 511)
    private Date dateTime;
    @Column(name = "conn_status", length = 10)
    private String connectionStatus; //EXHAUSTED
    @Column(name = "reconn_ind")
    private boolean reconnectIndicator;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
