package com.example.eventmanagement.facade;

import com.example.eventmanagement.model.Payment.PaymentStatus;
import com.example.eventmanagement.model.Payment.PaymentType;

import java.time.LocalDateTime;

/**
 * DTO populated by {@link PaymentDetailsFacade} for the payment report.
 */
public class PaymentDetails implements Details {

    private Long id;
    private PaymentType type;
    private Double amount;
    private PaymentStatus status;
    private LocalDateTime paidAt;

    public PaymentDetails() {}

    public PaymentDetails(Long id, PaymentType type, Double amount,
                          PaymentStatus status, LocalDateTime paidAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.paidAt = paidAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PaymentType getType() { return type; }
    public void setType(PaymentType type) { this.type = type; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
}