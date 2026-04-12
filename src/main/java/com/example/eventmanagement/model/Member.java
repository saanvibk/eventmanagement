package com.example.eventmanagement.model;

import jakarta.persistence.*;
import com.example.eventmanagement.model.Club;

@Entity
public class Member {

    @Id
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String srn;
    

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    // Getters & Setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSrn() { return srn; }
    public void setSrn(String srn) { this.srn = srn; }

    public Club getClub() { return club; }
    public void setClub(Club club) { this.club = club; }
}