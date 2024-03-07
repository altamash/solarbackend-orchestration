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
@Table(name = "internationalization")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Internationalization {

    @Id
    @Column(name = "i18n_id", length = 3)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i18nId;
    @Column(name = "constant", length = 20)
    private String constant; //GREETING, THANKYOU, CONGRATULATIONS,
    @Column(name = "lang", length = 3)
    private String lang;
    @Column(name = "translation")
    private String translation;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
