package com.example.eventmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventmanagement.model.Club;
import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.model.MembershipRequest;
import com.example.eventmanagement.repository.ClubRepository;
import com.example.eventmanagement.repository.MemberRepository;
import com.example.eventmanagement.repository.MembershipRequestRepository;

@Service
public class MembershipRequestService {

    @Autowired
    private MembershipRequestRepository requestRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private ClubRepository clubRepo;

    public void approveRequest(Long requestId) {

        MembershipRequest request = requestRepo.findById(requestId).orElse(null);

        // FIX: was checking "REQUESTED" but status is saved as "PENDING"
        if (request != null && request.getStatus().equals("PENDING")) {

            request.setStatus("APPROVED");
            requestRepo.save(request);

            Member member = new Member();
            member.setName(request.getName());
            member.setEmail(request.getEmail());
            member.setSrn(request.getSrn());
            member.setStatus("ACTIVE");

            Club club = clubRepo.findById(request.getClubId()).orElse(null);
            member.setClub(club);

            memberRepo.save(member);
        }
    }
}