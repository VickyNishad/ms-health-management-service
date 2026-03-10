package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lead_status")
public class LeadStatus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status_name")
    private String statusName;


}