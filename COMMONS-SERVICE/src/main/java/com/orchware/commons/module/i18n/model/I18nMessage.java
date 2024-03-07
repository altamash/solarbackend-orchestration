package com.orchware.commons.module.i18n.model;

//import javax.persistence.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
//@Table(name = "i18n_message")
@Table(name = "internationalization")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I18nMessage {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 2)
    private String locale;
    @Column(name = "message_key")
    private String key;
    @Column(name = "message_content")
    private String content;*/

    @Id
    @Column(name = "i18n_id", length = 3)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long i18nId;
    @Column(name = "constant", length = 4)
    private String constant; //GREETING, THANKYOU, CONGRATULATIONS,
    @Column(name = "lang", length = 3)
    private String lang;
    @Column(name = "translation", length = 100)
    private String translation;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
