package com.example.eventmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.model.MembershipRequest;
import com.example.eventmanagement.repository.MemberRepository;
import com.example.eventmanagement.repository.MembershipRequestRepository;
import com.example.eventmanagement.service.MemberService;
import com.example.eventmanagement.strategy.impl.ApproveStrategy;
import com.example.eventmanagement.strategy.impl.RejectStrategy;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembershipRequestRepository requestRepository;

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

        if (approve) {
            approveStrategy.process(request);
            request.setStatus("APPROVED");
        } else {
            rejectStrategy.process(request);
            request.setStatus("REJECTED");
        }

        requestRepository.save(request);
    }
}