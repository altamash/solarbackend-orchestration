package com.orchware.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;

@Entity
@Table(name = "platform_subscription_levels")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformSubscriptionLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_level_id", length = 25)
    private Long subscriptionLevelId;
    @Column(name = "name", length = 25)
    private String Name;
    @Column(name = "description", length = 25)
    private String Description;
}
