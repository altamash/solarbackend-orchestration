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
@Table(name = "organization")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @Column(name = "org_id", length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgId;
    private Long accountId;

    @Column(name = "org_id_by_acct", length = 4)
    private String orgIdByAcct; //unique alpha number random generated code 10char
    @Column(name = "orgName", length = 30)
    private String orgName;
    @Column(name = "orgCode", length = 3)
    private Long orgCode;
    @Column(name = "orgType", length = 10)
    private String orgType;
    @Column(name = "primaryInd")
    private boolean primaryInd;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
