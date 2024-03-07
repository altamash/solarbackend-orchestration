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
@Table(name = "dev_reg_archs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRegistrationArchive {

    @Id
    @Column(name = "device_reg_arch_id", length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceRegistrationArchiveId;
    @Column(name = "device_pool_id", length = 6)
    private Long devicePoolId;
    private Long accountUserId;
    @Column(name = "device_type", length = 10)
    private String deviceType;
    @Column(name = "brand_id", length = 15)
    private String brandId;
    @Column(name = "device_id", length = 15)
    private String deviceId;
    @Column(name = "connectivity_type", length = 8)
    private String connectivityType; // WIRELESS
    @Column(name = "ipAddress", length = 45)
    private String ip_address;
    @Column(name = "auth_type", length = 8)
    private String auth_type;
    @Column(name = "userName", length = 30)
    private String userName;
    @Column(name = "password", length = 65)
    private String password;
    @Column(name = "sec_key", length = 65)
    private String secretKey;
    @Column(name = "location", length = 50)
    private String location;
    @Column(name = "premise_type", length = 20)
    private String premiseType;
    @Column(name = "reg_date")
    private Date regDate;
    @Column(name = "status", length = 10)
    private String status; //CONNECTING, NEW,
    @Column(name = "retry_count", length = 2)
    private Long retryCount;
    @Column(name = "current_agent_id", length = 6)
    private String currentAgentId;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
