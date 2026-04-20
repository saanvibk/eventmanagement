package com.example.eventmanagement.controller;

import com.example.eventmanagement.facade.ClubDetails;
import com.example.eventmanagement.facade.EventDetails;
import com.example.eventmanagement.facade.PaymentDetails;
import com.example.eventmanagement.model.Payment;
import com.example.eventmanagement.model.Payment.PaymentStatus;
import com.example.eventmanagement.model.Payment.PaymentType;
import com.example.eventmanagement.service.PaymentGenerationService;
import com.example.eventmanagement.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

/**
 * PaymentController – MVC Controller for Payment Management (Mem4).
 *
 * Routes:
 *   GET  /payments                   → list payments for the current member
 *   GET  /payments/new               → show create/pay form
 *   POST /payments/new               → record a new payment
 *   GET  /payments/{id}              → view payment detail
 *   POST /payments/{id}/confirm      → mark paid / confirm
 *   POST /payments/{id}/fail         → mark failed
 *   POST /payments/{id}/delete       → delete payment record
 *   GET  /payments/{id}/report       → generate payment report (Facade)
 *   GET  /payments/fines             → list outstanding fines (stub)
 *   POST /payments/fines/{id}/pay    → pay a fine (stub)
 *   POST /payments/fines/{id}/waive  → waive a fine (stub)
 */
@Controller
@RequestMapping("/payments")
public class PaymentController {

    // For demo: replace with Spring Security principal in real app.
    private static final Long DEMO_MEMBER_ID = 1L;

    private final PaymentService paymentService;
    private final PaymentGenerationService paymentGenerationService;

    @Autowired
    public PaymentController(PaymentService paymentService,
                             PaymentGenerationService paymentGenerationService) {
        this.paymentService = paymentService;
        this.paymentGenerationService = paymentGenerationService;
    }

    // ========================
    //  List
    // ========================

    @GetMapping
    public String listPayments(Model model) {
        List<Payment> payments = paymentService.findByMember(DEMO_MEMBER_ID);
        model.addAttribute("payments", payments);
        model.addAttribute("statuses", PaymentStatus.values());
        model.addAttribute("types", PaymentType.values());
        model.addAttribute("totalPaid",
                paymentService.totalByStatus(DEMO_MEMBER_ID, PaymentStatus.CONFIRMED));
        model.addAttribute("totalPending",
                paymentService.totalByStatus(DEMO_MEMBER_ID, PaymentStatus.PENDING));
        model.addAttribute("totalFines", 0.0);
        return "payments/list";
    }

    // ========================
    //  Create
    // ========================

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("types", PaymentType.values());
        return "payments/create";
    }

    @PostMapping("/new")
    public String createPayment(@ModelAttribute Payment payment,
                                RedirectAttributes redirectAttrs) {
        try {
            Payment saved = paymentService.savePayment(payment);
            redirectAttrs.addFlashAttribute("successMessage",
                    "Payment #" + saved.getId() + " recorded.");
            return "redirect:/payments/" + saved.getId();
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/payments/new";
        }
    }

    // ========================
    //  Detail
    // ========================

    @GetMapping("/{id}")
    public String viewPayment(@PathVariable Long id, Model model) {
        Payment payment = paymentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + id));
        model.addAttribute("payment", payment);
        return "payments/detail";
    }

    // ========================
    //  State Transitions
    // ========================

    @PostMapping("/{id}/confirm")
    public String confirmPayment(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            Payment p = paymentService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + id));
            if (p.getStatus() == PaymentStatus.PENDING) {
                paymentService.markPaid(id);
            } else {
                paymentService.confirmPayment(id);
            }
            redirectAttrs.addFlashAttribute("successMessage", "Payment updated.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/payments/" + id;
    }

    @PostMapping("/{id}/fail")
    public String failPayment(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            paymentService.failPayment(id);
            redirectAttrs.addFlashAttribute("successMessage", "Payment marked as failed.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/payments/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deletePayment(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            paymentService.deletePayment(id);
            redirectAttrs.addFlashAttribute("successMessage", "Payment deleted.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/payments";
    }

    // ========================
    //  Report (Facade pattern)
    // ========================

    @GetMapping("/{id}/report")
    public String generateReport(@PathVariable Long id, Model model) {
        Payment payment = paymentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + id));

        EventDetails eventDetails = payment.getEventId() != null
                ? paymentGenerationService.getEventDetails(payment.getEventId())
                : null;
        ClubDetails clubDetails = payment.getClubId() != null
                ? paymentGenerationService.getClubDetails(payment.getClubId())
                : null;
        PaymentDetails paymentDetails = paymentGenerationService.getPaymentDetails(id);

        model.addAttribute("payment", payment);
        model.addAttribute("eventDetails", eventDetails);
        model.addAttribute("clubDetails", clubDetails);
        model.addAttribute("paymentDetails", paymentDetails);
        model.addAttribute("totalPaid",
                paymentService.totalByStatus(DEMO_MEMBER_ID, PaymentStatus.CONFIRMED));
        model.addAttribute("totalPending",
                paymentService.totalByStatus(DEMO_MEMBER_ID, PaymentStatus.PENDING));
        model.addAttribute("totalFines", 0.0);
        return "payments/report";
    }

    // ========================
    //  Fines (stub)
    // ========================

    @GetMapping("/fines")
    public String listFines(Model model) {
        // Stub: fine_notifications table not yet implemented.
        model.addAttribute("fines", Collections.emptyList());
        return "payments/fines";
    }

    @PostMapping("/fines/{id}/pay")
    public String payFine(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("successMessage", "Fine #" + id + " marked as paid.");
        return "redirect:/payments/fines";
    }

    @PostMapping("/fines/{id}/waive")
    public String waiveFine(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("successMessage", "Fine #" + id + " waived.");
        return "redirect:/payments/fines";
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