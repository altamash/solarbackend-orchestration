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
@Table(name = "error_definitions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDefinition {

    @Id
    @Column(name = "err_id", length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errId;
    @Column(name = "errPrefix", length = 4)
    private String errPrefix; //INFO, WARN, CRTL, DBUG, FATL based on level.
    @Column(name = "summary", length = 128)
    private String summary;
    @Column(name = "description", length = 511)
    private String description;
    @Column(name = "notes", length = 511)
    private Long notes;
    @Column(name = "error_level", length = 4)
    private String error_level; //INFO_WARN, CRITICAL, DEBUG, FATAL
    @Column(name = "lang_constant", length = 3)
    private String lang_constant; //reference from internationalization
    @Column(name = "var1", length = 5)
    private String var1;
    @Column(name = "var2", length = 5)
    private String var2;
    @Column(name = "var3", length = 5)
    private Long var3;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
