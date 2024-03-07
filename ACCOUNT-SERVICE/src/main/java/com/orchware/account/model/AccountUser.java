package com.orchware.account.model;

import com.orchware.account.model.accountType.AccountUserType;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "account_users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUser {

    @Id
    @Column(name = "user_id", length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToOne(mappedBy = "accountUser")
    private UserAuth userAuth;
    private Long accountId;
    @ManyToMany(targetEntity = AccountUserType.class)
    @JoinTable(name = "subscription",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<AccountUserType> accountUserTypes;
    @Column(name = "first_name", length = 25)
    private String firstName;
    @Column(name = "middle_name", length = 65)
    private String middleName;
    @Column(name = "last_name", length = 25)
    private String lastName;
    @Column(name = "phone", length = 10)
    private String phone;
    @Column(name = "user_type", length = 7)
    private String userType; //PRIMARY, LINKED

    @Column(name = "p_verified") //phone verified
    private boolean pVerified;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "e_verified")
    private boolean eVerified;
    @Column(name = "add1", length = 50)
    private String add1;
    @Column(name = "add2", length = 50)
    private String add2;
    @Column(name = "add3", length = 50)
    private String add3;
    @Column(name = "state", length = 25) //TODO: Purpose DataType?
    private Long state;
    @Column(name = "postal_code", length = 16)
    private String postalCode;
    @Column(name = "po_box", length = 16)
    private String poBox;
    @Column(name = "country", length = 50)
    private String country;
    @Column(name = "owner_ind")
    private boolean ownerInd;
    @Column(name = "role", length = 25) //TODO: Purpose DataType?
    private Long role;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
