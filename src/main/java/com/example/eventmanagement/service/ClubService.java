package com.example.eventmanagement.service;

import com.example.eventmanagement.model.Club;
import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.repository.ClubRepository;
import com.example.eventmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.eventmanagement.factory.ClubFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepo;

    @Autowired
    private MemberRepository memberRepo;

    // 🔹 Create club
    public Club createClub(Club club) {

        Club newClub = ClubFactory.createClub(
                club.getName(),
                club.getDescription(),
                club.getLeader()
        );

        return clubRepo.save(newClub);
    }

    // 🔹 Get all clubs
    public List<Club> getAllClubs() {
        return clubRepo.findAll();
    }

    // 🔹 Join club (UPDATED 🔥)
    public Member joinClub(Long clubId, Member member) {

        Club club = clubRepo.findById(clubId).orElse(null);

        if (club != null) {
            member.setClub(club);

            // timestamps (match DB)
            member.setCreatedAt(LocalDateTime.now());
            member.setUpdatedAt(LocalDateTime.now());

            return memberRepo.save(member);
        }
        return null;
    }

    // 🔹 Change leader
    public Club changeLeader(Long clubId, String newLeader) {

        Club club = clubRepo.findById(clubId).orElse(null);

        if (club != null) {
            club.setLeader(newLeader);
            club.setUpdatedAt(LocalDateTime.now());
            return clubRepo.save(club);
        }
        return null;
    }

    // 🔹 Get members
    public List<Member> getMembers(Long clubId) {
        return memberRepo.findByClubId(clubId);
    }

    // 🔹 Get single club
    public Club getClubById(Long clubId) {
        return clubRepo.findById(clubId).orElse(null);
    }
}