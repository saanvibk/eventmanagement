package com.example.eventmanagement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventmanagement.factory.ClubFactory;
import com.example.eventmanagement.model.Club;
import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.model.MembershipRequest;
import com.example.eventmanagement.repository.ClubRepository;
import com.example.eventmanagement.repository.MemberRepository;
import com.example.eventmanagement.repository.MembershipRequestRepository;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private MembershipRequestRepository membershipRequestRepo;

    public Club createClub(Club club) {
        Club newClub = ClubFactory.createClub(
                club.getName(),
                club.getDescription(),
                club.getLeader()
        );
        return clubRepo.save(newClub);
    }

    public List<Club> getAllClubs() {
        return clubRepo.findAll();
    }

    public Member joinClub(Long clubId, Member member) {

        Club club = clubRepo.findById(clubId).orElse(null);

        if (club != null) {

            MembershipRequest request = new MembershipRequest();

            request.setName(member.getName());
            request.setEmail(member.getEmail());
            request.setSrn(member.getSrn());
            request.setClubId(clubId);
            request.setStatus("PENDING");   // ← was "REQUESTED", fixed to match approval logic

            membershipRequestRepo.save(request);

            return null;
        }

        return null;
    }

    public Club changeLeader(Long clubId, String newLeader) {

        Club club = clubRepo.findById(clubId).orElse(null);

        if (club != null) {
            club.setLeader(newLeader);
            club.setUpdatedAt(LocalDateTime.now());
            return clubRepo.save(club);
        }

        return null;
    }

    public List<Member> getMembers(Long clubId) {
        return memberRepo.findByClubId(clubId);
    }

    public Club getClubById(Long clubId) {
        return clubRepo.findById(clubId).orElse(null);
    }
}