package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leads")
public class Lead extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lead_name")
    private String leadName;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private LeadSource source;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private LeadStatus status;


}