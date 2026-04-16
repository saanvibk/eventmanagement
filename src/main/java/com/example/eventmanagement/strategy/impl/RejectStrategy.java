package com.example.eventmanagement.strategy.impl;

import org.springframework.stereotype.Component;

import com.example.eventmanagement.model.MembershipRequest;
import com.example.eventmanagement.strategy.MembershipStrategy;

@Component
public class RejectStrategy implements MembershipStrategy {

    @Override
    public void process(MembershipRequest request) {
        System.out.println("Rejected request ID: " + request.getId());
    }
}