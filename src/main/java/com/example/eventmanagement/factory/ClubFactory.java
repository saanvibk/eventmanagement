package com.example.eventmanagement.factory;

import com.example.eventmanagement.model.Club;

public class ClubFactory {

    public static Club createClub(String name, String description, String leader) {
        Club club = new Club();
        club.setName(name);
        club.setDescription(description);
        club.setLeader(leader);
        return club;
    }
}