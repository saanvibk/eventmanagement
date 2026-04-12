package com.example.eventmanagement.service;

import com.example.eventmanagement.model.Club;
import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.repository.ClubRepository;
import com.example.eventmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.eventmanagement.factory.ClubFactory;
import java.util.List;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepo;

    @Autowired
    private MemberRepository memberRepo;

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
            member.setClub(club);
            return memberRepo.save(member);
        }
        return null;
    }

    public Club changeLeader(Long clubId, String newLeader) {
        Club club = clubRepo.findById(clubId).orElse(null);
        if (club != null) {
            club.setLeader(newLeader);
            return clubRepo.save(club);
        }
        return null;
    }

    // 🔥 ADD THIS
    public List<Member> getMembers(Long clubId) {
    Club club = clubRepo.findById(clubId).orElse(null);
    if (club != null) {
        return club.getMembers();
    }
    return null;
}

 public Club getClubById(Long clubId) {
    return clubRepo.findById(clubId).orElse(null);
}    

}