package com.example.eventmanagement.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.model.MembershipRequest;
import com.example.eventmanagement.repository.MemberRepository;
import com.example.eventmanagement.strategy.MembershipStrategy;

@Component
public class ApproveStrategy implements MembershipStrategy {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void process(MembershipRequest request) {
        Member member = new Member(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                "ACTIVE"
        );

        memberRepository.save(member);
    }
}