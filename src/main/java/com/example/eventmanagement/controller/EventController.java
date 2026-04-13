package com.example.eventmanagement .controller;

import java .util.List;

import org.springframework.beans.factory .annotation.Autowired;
import org .springframework.stereotype.Controller;
import org.springframework .ui.Model;
import org.springframework.web.bind .annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.eventmanagement.dto.EventSearchDTO;
import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.Event.EventStatus;
import com.example.eventmanagement.model.EventRegistration;
import com.example.eventmanagement.service.EventService;

/**
 * EventController – MVC Controller for Event Management (Mem2).
 *
 * Routes:
 *   GET  /events              → list all events
 *   GET  /events/new          → show create form
 *   POST /events/new          → create event
 *   GET  /events/{id}         → view event detail
 *   GET  /events/{id}/edit    → show edit form
 *   POST /events/{id}/edit    → update event
 *   POST /events/{id}/delete  → delete event
 *   POST /events/{id}/publish → publish event (state change)
 *   POST /events/{id}/start   → start event
 *   POST /events/{id}/complete→ complete event
 *   POST /events/{id}/cancel  → cancel event
 *   GET  /events/search       → search/filter events (Minor Feature)
 *   POST /events/{id}/register→ register member
 *   POST /events/{id}/unregister → cancel registration
 *   GET  /events/{id}/registrations → view registrations
 */
@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // ========================
    //  List All Events
    // ========================

    @GetMapping
    public String listEvents(Model model) {
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        model.addAttribute("statuses", EventStatus.values());
        return "events/list";
    }

    // ========================
    //  Create Event
    // ========================

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", List.of("Technical", "Cultural", "Sports", "Academic", "Social", "Workshop"));
        return "events/create";
    }

    @PostMapping("/new")
    public String createEvent(@ModelAttribute Event event,
                              RedirectAttributes redirectAttrs) {
        try {
            Event created = eventService.createEvent(event);
            redirectAttrs.addFlashAttribute("successMessage",
                    "Event '" + created.getTitle() + "' created successfully!");
            return "redirect:/events/" + created.getId();
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/events/new";
        }
    }

    // ========================
    //  View Event Detail
    // ========================

    @GetMapping("/{id}")
    public String viewEvent(@PathVariable Long id, Model model) {
        Event event = eventService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + id));
        long regCount = eventService.getRegistrationCount(id);
        model.addAttribute("event", event);
        model.addAttribute("registrationCount", regCount);
        // For demo: pass a mock current member id (in real app, use Spring Security)
        model.addAttribute("currentMemberId", 1L);
        model.addAttribute("isRegistered", eventService.isMemberRegistered(id, 1L));
        return "events/detail";
    }

    // ========================
    //  Edit Event
    // ========================

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Event event = eventService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + id));
        model.addAttribute("event", event);
        model.addAttribute("categories", List.of("Technical", "Cultural", "Sports", "Academic", "Social", "Workshop"));
        return "events/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateEvent(@PathVariable Long id,
                              @ModelAttribute Event event,
                              RedirectAttributes redirectAttrs) {
        try {
            eventService.updateEvent(id, event);
            redirectAttrs.addFlashAttribute("successMessage", "Event updated successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/events/" + id;
    }

    // ========================
    //  Delete Event
    // ========================

    @PostMapping("/{id}/delete")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            eventService.deleteEvent(id);
            redirectAttrs.addFlashAttribute("successMessage", "Event deleted.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/events";
    }

    // ========================
    //  State Transitions (Activity Diagram flows)
    // ========================

    @PostMapping("/{id}/publish")
    public String publishEvent(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            eventService.publishEvent(id);
            redirectAttrs.addFlashAttribute("successMessage",
                    "Event published! Notifications sent to members.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/events/" + id;
    }

    @PostMapping("/{id}/start")
    public String startEvent(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            eventService.startEvent(id);
            redirectAttrs.addFlashAttribute("successMessage", "Event marked as Ongoing.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/events/" + id;
    }

    @PostMapping("/{id}/complete")
    public String completeEvent(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            eventService.completeEvent(id);
            redirectAttrs.addFlashAttribute("successMessage", "Event marked as Completed.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/events/" + id;
    }

    @PostMapping("/{id}/cancel")
    public String cancelEvent(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            eventService.cancelEvent(id);
            redirectAttrs.addFlashAttribute("successMessage",
                    "Event cancelled. Notifications sent to registered members.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/events/" + id;
    }

    // ========================
    //  Search & Filter (Minor Feature)
    // ========================

    @GetMapping("/search")
    public String searchEvents(@ModelAttribute EventSearchDTO searchDTO, Model model) {
        List<Event> results = eventService.searchEvents(searchDTO);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("events", results);
        model.addAttribute("statuses", EventStatus.values());
        model.addAttribute("categories",
                List.of("Technical", "Cultural", "Sports", "Academic", "Social", "Workshop"));
        return "events/search";
    }

    // ========================
    //  Registration
    // ========================

    @PostMapping("/{id}/register")
    public String registerMember(@PathVariable Long id,
                                 @RequestParam Long memberId,
                                 RedirectAttributes redirectAttrs) {
        try {
            eventService.registerMember(id, memberId);
            redirectAttrs.addFlashAttribute("successMessage", "Registered successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/events/" + id;
    }

    @PostMapping("/{id}/unregister")
    public String cancelRegistration(@PathVariable Long id,
                                     @RequestParam Long memberId,
                                     RedirectAttributes redirectAttrs) {
        try {
            eventService.cancelRegistration(id, memberId);
            redirectAttrs.addFlashAttribute("successMessage", "Registration cancelled.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/events/" + id;
    }

    @GetMapping("/{id}/registrations")
    public String viewRegistrations(@PathVariable Long id, Model model) {
        Event event = eventService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + id));
        List<EventRegistration> registrations = eventService.getRegistrations(id);
        model.addAttribute("event", event);
        model.addAttribute("registrations", registrations);
        model.addAttribute("registrationCount", eventService.getRegistrationCount(id));
        return "events/registrations";
    }

    // ========================
    //  Exception Handler
    // ========================

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleNotFound(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
