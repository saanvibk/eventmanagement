package com.example.eventmanagement.strategy;

import com.example.eventmanagement.model.MembershipRequest;

public interface MembershipStrategy {
    void process(MembershipRequest request);
}