package com.example.eventmanagement.service;

import com.example.eventmanagement.facade.ClubDetails;
import com.example.eventmanagement.facade.EventDetails;
import com.example.eventmanagement.facade.PaymentDetails;

/**
 * PaymentGenerationService – contract for assembling a payment report
 * from the Event, Club, and Payment subsystems.
 *
 * ✅ Design Pattern: Facade
 *    - Each getter delegates to a concrete {@code DetailsFacade} instead of
 *      talking to each subsystem directly.
 */
public interface PaymentGenerationService {

    EventDetails getEventDetails(Long eventId);

    ClubDetails getClubDetails(Long clubId);

    PaymentDetails getPaymentDetails(Long paymentId);
}