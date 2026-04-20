package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.facade.ClubDetails;
import com.example.eventmanagement.facade.ClubDetailsFacade;
import com.example.eventmanagement.facade.EventDetails;
import com.example.eventmanagement.facade.EventDetailsFacade;
import com.example.eventmanagement.facade.PaymentDetails;
import com.example.eventmanagement.facade.PaymentDetailsFacade;
import com.example.eventmanagement.service.PaymentGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PaymentGenerationServiceImpl – assembles a payment report by delegating
 * to one facade per subsystem.
 *
 * ✅ Design Pattern  : Facade Pattern
 * ✅ Design Principle: Dependency Inversion Principle (DIP)
 */
@Service
public class PaymentGenerationServiceImpl implements PaymentGenerationService {

    private final EventDetailsFacade eventDetailsFacade;
    private final ClubDetailsFacade clubDetailsFacade;
    private final PaymentDetailsFacade paymentDetailsFacade;

    @Autowired
    public PaymentGenerationServiceImpl(EventDetailsFacade eventDetailsFacade,
                                        ClubDetailsFacade clubDetailsFacade,
                                        PaymentDetailsFacade paymentDetailsFacade) {
        this.eventDetailsFacade = eventDetailsFacade;
        this.clubDetailsFacade = clubDetailsFacade;
        this.paymentDetailsFacade = paymentDetailsFacade;
    }

    @Override
    public EventDetails getEventDetails(Long eventId) {
        return (EventDetails) eventDetailsFacade.getDetails(eventId);
    }

    @Override
    public ClubDetails getClubDetails(Long clubId) {
        return (ClubDetails) clubDetailsFacade.getDetails(clubId);
    }

    @Override
    public PaymentDetails getPaymentDetails(Long paymentId) {
        return (PaymentDetails) paymentDetailsFacade.getDetails(paymentId);
    }
}