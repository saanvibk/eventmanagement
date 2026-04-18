package com.example.eventmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MembershipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String srn;      //  ADD THIS
    private Long clubId;     //  ADD THIS
    private String status;

    public MembershipRequest() {}

    public MembershipRequest(String name, String email, String srn, Long clubId, String status) {
        this.name = name;
        this.email = email;
        this.srn = srn;
        this.clubId = clubId;
        this.status = status;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getSrn() { return srn; }
    public Long getClubId() { return clubId; }
    public String getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setSrn(String srn) { this.srn = srn; }
    public void setClubId(Long clubId) { this.clubId = clubId; }
    public void setStatus(String status) { this.status = status; }
}