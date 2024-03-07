package com.orchware.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;

@Entity
@Table(name = "agent_smart_keys")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentSmartKeys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "smart_key_id", length = 25)
    private Long smartKeyId;
    @Column(name = "smart_key_hash", length = 25)
    private String smartKeyHash;
    @Column(name = "private_key", length = 25)
    private String privateKey;
    @Column(name = "public_key", length = 25)
    private String publicKey;
    @Column(name = "agent_id_reserved", length = 25)
    private String agentIdReserved;
    @Column(name = "last_regen_datetime", length = 25)
    private String lastRegenDatetime;
    @Column(name = "last_regen_by", length = 25)
    private String lastRegenBy;
    @Column(name = "temp_ind", length = 25)
    private String tempInd;
}
