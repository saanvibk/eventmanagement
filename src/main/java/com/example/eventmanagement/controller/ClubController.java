package com.example.eventmanagement.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.eventmanagement.model.Club;
import com.example.eventmanagement.model.Member;
import com.example.eventmanagement.service.ClubService;

@Controller
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService service;

    // 🔥 Constructor Injection
    public ClubController(ClubService service) {
        this.service = service;
    }

    // ========================
    //  View All Clubs
    // ========================
    @GetMapping
    public String getAllClubs(Model model) {
        List<Club> clubs = service.getAllClubs();
        model.addAttribute("clubs", clubs);
        return "clubs/view";
    }

    // ========================
    //  Create Club
    // ========================
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("club", new Club());
        return "clubs/create";
    }

    @PostMapping("/create")
    public String createClub(@ModelAttribute Club club,
                             RedirectAttributes redirectAttrs) {
        try {
            service.createClub(club);
            redirectAttrs.addFlashAttribute("successMessage", "Club created successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/clubs";
    }

    // ========================
    //  Join Club (FIXED)
    // ========================
    @GetMapping("/join")
    public String showJoinPage() {
        return "clubs/join";
    }

    @PostMapping("/join")
    public String joinClub(@RequestParam Long clubId,
                           @RequestParam String name,
                           @RequestParam String srn,
                           @RequestParam String email,
                           RedirectAttributes redirectAttrs) {

        try {
            Member member = new Member();

            member.setName(name);
            member.setSrn(srn);
            member.setEmail(email);

            // ❌ IMPORTANT: DO NOT SET ROLE OR STATUS HERE
            // member.setRole("STUDENT");
            // member.setStatus("ACTIVE");

            // ✅ Send to MembershipRequest flow
            service.joinClub(clubId, member);

            redirectAttrs.addFlashAttribute("successMessage", "Request sent successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/clubs/" + clubId;
    }

    // ========================
    //  Change Leader
    // ========================
    @GetMapping("/leader")
    public String showLeaderPage() {
        return "clubs/leader";
    }

    @PostMapping("/{clubId}/leader")
    public String changeLeader(@PathVariable Long clubId,
                               @RequestParam String newLeader,
                               RedirectAttributes redirectAttrs) {

        try {
            service.changeLeader(clubId, newLeader);
            redirectAttrs.addFlashAttribute("successMessage", "Leader updated!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/clubs/" + clubId;
    }

    // ========================
    //  Club Details
    // ========================
    @GetMapping("/{clubId:\\d+}")
    public String getClubDetails(@PathVariable Long clubId, Model model) {

        Club club = service.getClubById(clubId);

        if (club == null) {
            return "redirect:/clubs";
        }

        model.addAttribute("club", club);
        model.addAttribute("members", service.getMembers(clubId));

        return "clubs/details";
    }
}