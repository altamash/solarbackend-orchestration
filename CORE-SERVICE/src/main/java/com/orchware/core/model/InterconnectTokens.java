package com.orchware.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;

@Entity
@Table(name = "interconnect_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterconnectTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secKeyId", length = 25)
    private Long secKeyId;
    private Long accountId;

    @Column(name = "active_token_issued", length = 25)
    private String activeTokenIssued;
    @Column(name = "expiry_date", length = 25)
    private String expiryDate;
    @Column(name = "state", length = 25)
    private String state;
}
