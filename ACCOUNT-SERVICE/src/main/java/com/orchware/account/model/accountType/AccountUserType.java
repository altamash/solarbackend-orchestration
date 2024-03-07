package com.orchware.account.model.accountType;

import com.orchware.account.model.AccountUser;
import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "account_user_type")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true)
    private String name;

    @ManyToMany(mappedBy = "accountUserTypes")
    private Set<AccountUser> accountUsers;
}
