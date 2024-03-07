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
import java.util.TimeZone;

@Entity
@Table(name = "registered_devices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", length = 10)
    private Long deviceId;
//    @Column(name = "device_type_id", length = 20)
//    private String deviceTypeId;
//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_type_id")
    private DeviceTypeConfig deviceTypeConfig;
    @Column(name = "alias", length = 10)
    private String alias;
    @Column(name = "auth_method", length = 10)
    private String authMethod;
    @Column(name = "password", length = 10)
    private String password;
    @Column(name = "conn_key", length = 150)
    private String connectionKey;
    @Column(name = "conn_key_2", length = 150)
    private String connectionKey2;
    @Column(name = "username", length = 10)
    private String username;
    @Column(name = "unique_id", length = 15)
    private String uniqueId;
    @Column(name = "state", length = 15)
    private String state; //Operational, Unreachable, PendingConfig, Warning, Failed
    @Column(name = "warning_level", length = 10)
    private String warningLevel; //Number 1-5
    // 5 means critical and 1 means information level warning. if no warning then 0
    @Column(name = "warning_details", length = 1)
    private Long warningDetails;
    @Column(name = "mobility", length = 6)
    private String mobility;
    @Column(name = "current_loc", length = 50)
    private String currentLoc;
    @Column(name = "timeZone")
    private TimeZone timeZone;
    @Column(name = "schedule_id", length = 6)
    private Long scheduleId; //from comm_schedule_tz
    @Column(name = "contact_schedule_id", length = 6)
    private Long contactScheduleId; //inherited from brand if empty.
    @Column(name = "active_start_time")
    private Date activeStartTime;
    @Column(name = "active_end_time")
    private Date activeEndTime;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private DeviceVarExt deviceVarExt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
