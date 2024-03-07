package com.orchware.account.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acct_id", length = 10)
    private Long acctId;
    @Column(name = "acct_name", length = 25)
    private String acctName;
    @Column(name = "acct_type", length = 25)
    private String acctType;
    @Column(name = "business_name", length = 30)
    private String businessName;
    @Column(name = "status", length = 8)
    private String status;
    private LocalDateTime activatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
