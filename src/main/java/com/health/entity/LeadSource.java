package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lead_sources")
public class LeadSource extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_name")
    private String sourceName;

}