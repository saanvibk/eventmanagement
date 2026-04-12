package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.Club;
import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {

    @Autowired
    private ClubService service;

    // Create Club
    @PostMapping
    public Club createClub(@RequestBody Club club) {
        return service.createClub(club);
    }

    // Get all clubs
    @GetMapping
    public List<Club> getAllClubs() {
        return service.getAllClubs();
    }

    // Join Club
    @PostMapping("/{clubId}/join")
    public Member joinClub(@PathVariable Long clubId, @RequestBody Member member) {
        return service.joinClub(clubId, member);
    }

    // Change Leader
    @PutMapping("/{clubId}/leader")
    public Club changeLeader(@PathVariable Long clubId, @RequestParam String newLeader) {
        return service.changeLeader(clubId, newLeader);
    }

    @GetMapping("/{clubId}/members")
    public List<Member> getMembers(@PathVariable Long clubId) {
    return service.getMembers(clubId);
    }
    
    @GetMapping("/{clubId}")
public Club getClubById(@PathVariable Long clubId) {
    return service.getClubById(clubId);
}
}


