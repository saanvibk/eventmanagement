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

import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // ── List all members ──────────────────────────────────────────
    @GetMapping
    public String listMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "members/list";
    }

    // ── Admin: view & process membership requests ─────────────────
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

    // ── Profile Update ────────────────────────────────────────────
    @GetMapping("/{id}/edit")
    public String editProfile(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberService.getMemberById(id));
        return "members/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateProfile(@PathVariable Long id,
                                @ModelAttribute Member updatedMember) {
        memberService.updateMember(id, updatedMember);
        return "redirect:/members";
    }

    // ── Leave Club ────────────────────────────────────────────────
    @PostMapping("/{id}/leave")
    public String leaveClub(@PathVariable Long id) {
        memberService.leaveMember(id);
        return "redirect:/members";
    }
}