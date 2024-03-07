package com.orchware.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;

@Entity
@Table(name = "agents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_id", length = 25)
    private Long agentId;
    @Column(name = "brand_id", length = 25)
    private String brandId;
    @Column(name = "agent_type", length = 25)
    private String agentType;
    @Column(name = "agent_loc", length = 25)
    private String agentLoc;
    @Column(name = "agent_key", length = 25)
    private String agentKey;
    @Column(name = "location", length = 25)
    private String location;
    @Column(name = "hash", length = 25)
    private String hash;
    @Column(name = "state", length = 25)
    private String state;
    @Column(name = "reserve_date", length = 25)
    private String reserveDate;
    @Column(name = "hit_ratio", length = 25)
    private String hitRatio;
    @Column(name = "interconnect_key", length = 25)
    private String interconnectKey;
    @Column(name = "var1", length = 25)
    private String var1;
    @Column(name = "var2", length = 25)
    private String var2;
    @Column(name = "var3", length = 25)
    private String var3;
}
