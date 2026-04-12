package com.example.eventmanagement.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Club {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String leader;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    @JsonIgnore   // 🔥 important
    private List<Member> members;

    // ✅ ADD THIS GETTER
    public List<Member> getMembers() {
        return members;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLeader() { return leader; }
    public void setLeader(String leader) { this.leader = leader; }
}