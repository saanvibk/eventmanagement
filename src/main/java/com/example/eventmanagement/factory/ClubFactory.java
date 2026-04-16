package com.example.eventmanagement.factory;

import com.example.eventmanagement.model.Club;
import java.time.LocalDateTime;

public class ClubFactory {

    public static Club createClub(String name, String description, String leader) {

        Club club = new Club();

        club.setName(name);
        club.setDescription(description);
        club.setLeader(leader);

        // 🔥 ADD THESE (MANDATORY)
        club.setCategory("General");
        club.setStatus("ACTIVE");
        club.setCreatedBy(1);  // or parse leader if needed
        club.setCreatedAt(LocalDateTime.now());
        club.setUpdatedAt(LocalDateTime.now());
        return club;
    }
}