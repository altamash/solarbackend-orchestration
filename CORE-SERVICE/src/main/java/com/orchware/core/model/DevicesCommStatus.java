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
@Table(name = "devices_comm_statuses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevicesCommStatus {

    @Id
    @Column(name = "device_comm_status_id", length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long devicesCommStatusId;

    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "org_id")
    private Organization organization;
    @Column(name = "comm_type", length = 6)
    private String commType; //HB, STATUS, OTHER
    @Column(name = "comm_status", length = 10)
    private String commStatus;
    @Column(name = "comm_time")
    private LocalDateTime commTime;
    @Column(name = "agent_id", length = 20)
    private String agentId;
    @Column(name = "agent_ref_Id", length = 20)
    private String agentRefId;
    @Column(name = "err_ind")
    private boolean errInd;
    @Column(name = "err_code", length = 20)
    private String errCode;
    @Column(name = "err_message", length = 255)
    private String errMessage;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
