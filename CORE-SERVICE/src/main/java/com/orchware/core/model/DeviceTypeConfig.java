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
@Table(name = "device_type_configs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTypeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dev_type_id", length = 10)
    private Long devTypeId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "brand_id")
    private ManufacturerBrand manufacturerBrand;

    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "org_id")
    private Organization organization;
    @Column(name = "device_type", length = 10)
    private String deviceType;
    @Column(name = "device_sub_type", length = 10)
    private String deviceSubType;
    @Column(name = "agent_comms_strategy", length = 10)
    private String agentCommsStrategy;
    @Column(name = "alias", length = 15)
    private String alias;
    @Column(name = "connectivity_method", length = 10)
    private String connectivityMethod;
    @Column(name = "unique_id_method", length = 10)
    private String uniqueIdMethod;
    @Column(name = "global_status", length = 10)
    private String globalStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
