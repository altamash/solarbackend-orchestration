package com.orchware.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "interconnect_sec_keys")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterconnectSecKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sec_key_id")
    private Long secKeyId;
    private Long accountId;

    @Column(name = "corssource_id", length = 25)
    private String corssourceId;
    @Column(name = "keyhash", length = 5)
    private String keyhash;
    @Column(name = "unique_key", length = 10)
    private String uniqueKey;
    @Column(name = "active_ind")
    private Boolean activeInd;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "expiry_date")
    private Date expiryDate;
    @Column(name = "max_calls_per_day")
    private Integer maxCallsPerDay;
    @Column(name = "corsref_tag")
    private String corsrefTag;
    @Column(name = "refreshcors_ind")
    private Boolean refreshcorsInd;
    @Column(name = "locked_ind")
    private Boolean lockedInd;
    @Column(name = "failed_attempts")
    private Integer failedAttempts;
    @Column(name = "symmetric_algo", length = 10)
    private String symmetricAlgo;
    @Column(name = "asymmetric_algo", length = 10)
    private String asymmetricAlgo;
    @Column(name = "asymmetric_public_key", length = 500)
    private String asymmetricPublicKey;
    @Column(name = "asymmetric_private_key", length = 2000)
    private String asymmetricPrivateKey;
    @Column(name = "symmetric_secret_key")
    private String symmetricSecretKey;
    @Column(name = "time_stamp")
    private long timeStamp;
}
