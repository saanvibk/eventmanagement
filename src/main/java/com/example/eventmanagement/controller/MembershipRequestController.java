package com.example.eventmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.repository.MembershipRequestRepository;
import com.example.eventmanagement.service.MembershipRequestService;

@Controller
@RequestMapping("/requests")
public class MembershipRequestController {

    @Autowired
    private MembershipRequestRepository requestRepo;

    @Autowired
    private MembershipRequestService requestService;

    @GetMapping
    public String viewRequests(Model model) {
        model.addAttribute("requests", requestRepo.findAll());
        return "requests";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        requestService.approveRequest(id);
        return "redirect:/requests";
    }
}