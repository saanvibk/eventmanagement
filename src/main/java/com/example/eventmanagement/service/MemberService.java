package com.example.eventmanagement.service;

import java.util.List;

import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.model.MembershipRequest;

public interface MemberService {

    Member addMember(Member member);

    List<Member> getAllMembers();

    Member getMemberById(Long id);

    Member updateMember(Long id, Member updatedMember);

    void leaveMember(Long id);

    MembershipRequest createRequest(MembershipRequest request);

    List<MembershipRequest> getAllRequests();

    void processRequest(Long requestId, boolean approve);
}