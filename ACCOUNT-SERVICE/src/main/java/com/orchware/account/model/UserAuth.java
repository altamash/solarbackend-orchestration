package com.orchware.account.model;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_authentication")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {

    @Id
    @Column(name = "user_auth_id", length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAuthId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acct_user_id", referencedColumnName = "user_id")
    private AccountUser accountUser;
    @Transient
    private String jwtToken;
    @Column(name = "auth_type", length = 8)
    private String authType; // OAUTH, STANDARD,
    @Column(name = "username", length = 25)
    private String username;
    @Column(name = "password", length = 65)
    private String password;
    @Column(name = "auth_link_email", length = 10)
    private String authLinkEmail;
    @Column(name = "passcode", length = 65)
    private String passcode; //Hash
    @Column(name = "sec_ques_1", length = 100)
    private String secQues1;
    @Column(name = "sec_1_ans", length = 100)
    private String sec1Ans;
    @Column(name = "sec_ques_2", length = 100)
    private String secQues2;
    @Column(name = "sec_2_ans", length = 100)
    private String sec2Ans;
    @Column(name = "consec_fail_att_count", length = 2)
    private Long consecutiveFailedAttemptCount;
    @Column(name = "expired_ind")
    private boolean expiredInd;
    @Column(name = "next_exp_date", length = 25) //TODO: Purpose DataType?
    private Long nextExpireDate;
    @Column(name = "var1", length = 5)
    private String var1;
    @Column(name = "var2", length = 5)
    private String var2;
    @Column(name = "var3", length = 5)
    private String var3;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
