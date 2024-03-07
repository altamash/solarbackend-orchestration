package com.orchware.core.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "manufacture_brands")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerBrand {

    @Id
    @Column(name = "brand_id", length = 6)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    @Column(name = "brand_name", length = 15)
    private String brandName;
    @Column(name = "brand_category", length = 10)
    private String brandCategory;
    @Column(name = "parent_brand_id", length = 3)
    private Long parentBrandId;
    @Column(name = "manufacturer_name", length = 15)
    private String manufacturerName;
    @Column(name = "country_of_origin", length = 50)
    private String countryOfOrigin;
    @Column(name = "country_of_mfg", length = 50)
    private String countryOfManufacturing;
    @Column(name = "default_agent_comms_strategy", length = 15)
    private String defaultAgentCommsStrategy;
    @Column(name = "prefix", length = 10)
    private String prefix;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
