package com.example.eventmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.eventmanagement.model.MembershipRequest;
import com.example.eventmanagement.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String listMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "members/list";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("request", new MembershipRequest());
        return "members/join";
    }

    @PostMapping("/join")
    public String submit(@ModelAttribute MembershipRequest request) {
        memberService.createRequest(request);
        return "redirect:/members";
    }

    @GetMapping("/requests")
    public String requests(Model model) {
        model.addAttribute("requests", memberService.getAllRequests());
        return "members/requests";
    }

    @PostMapping("/process/{id}")
    public String process(@PathVariable Long id, @RequestParam boolean approve) {
        memberService.processRequest(id, approve);
        return "redirect:/members/requests";
    }
}