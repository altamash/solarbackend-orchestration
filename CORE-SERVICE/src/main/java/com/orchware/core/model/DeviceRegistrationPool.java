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
@Table(name = "dev_reg_pools")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRegistrationPool {

    @Id
    @Column(name = "device_pool_id", length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long devicePoolId;
    private Long accountId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "org_id")
    private Organization organization;
//    @Column(name = "device_type", length = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_type")
    private DeviceTypeConfig deviceTypeConfig;
//    @Column(name = "brand_id", length = 15)
//    private String brandId;
//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private ManufacturerBrand brand;
//    @Column(name = "device_id", length = 15)
//    private String deviceId;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private RegisteredDevice registeredDevice;
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
//    @Column(name = "current_agent_id", length = 6)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_agent_id")
    private Agent currentAgent;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
