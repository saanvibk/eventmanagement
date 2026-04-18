package com.example.eventmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.model.MembershipRequest;
import com.example.eventmanagement.repository.MemberRepository;
import com.example.eventmanagement.repository.MembershipRequestRepository;
import com.example.eventmanagement.service.MemberService;
import com.example.eventmanagement.strategy.MembershipStrategy;
import com.example.eventmanagement.strategy.impl.ApproveStrategy;
import com.example.eventmanagement.strategy.impl.RejectStrategy;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembershipRequestRepository requestRepository;

    // LSP: depend on the MembershipStrategy interface, not the concrete classes
    @Autowired
    private ApproveStrategy approveStrategy;

    @Autowired
    private RejectStrategy rejectStrategy;

    @Override
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found: " + id));
    }

    @Override
    public Member updateMember(Long id, Member updatedMember) {
        Member existing = getMemberById(id);
        existing.setName(updatedMember.getName());
        existing.setEmail(updatedMember.getEmail());
        existing.setPhone(updatedMember.getPhone());
        return memberRepository.save(existing);
    }

    @Override
    public void leaveMember(Long id) {
        Member member = getMemberById(id);
        member.setStatus("LEFT");
        memberRepository.save(member);
    }

    @Override
    public MembershipRequest createRequest(MembershipRequest request) {
        request.setStatus("PENDING");
        return requestRepository.save(request);
    }

    @Override
    public List<MembershipRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public void processRequest(Long requestId, boolean approve) {
        MembershipRequest request = requestRepository.findById(requestId).orElseThrow();

        // LSP in action: both strategies are used via the MembershipStrategy interface
        MembershipStrategy strategy = approve ? approveStrategy : rejectStrategy;
        strategy.process(request);
        request.setStatus(approve ? "APPROVED" : "REJECTED");

        requestRepository.save(request);
    }
}