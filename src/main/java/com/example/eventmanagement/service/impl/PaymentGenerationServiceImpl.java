package com.example.eventmanagement .service.impl;
package structural.facade;

//import com.example.eventmanagement .dto.EventSearchDTO;
import com.example.eventmanagement .model.Payment;
import com.example.eventmanagement .model.Payment.PaymentStatus;
import com.example.eventmanagement .model.EventRegistration;
//import com.example.eventmanagement .observer.EventObserver;
//import com.example.eventmanagement .observer.EventSubject;
//import com.example.eventmanagement .observer.impl.DashboardNotificationObserver;
//import com.example.eventmanagement .observer.impl.EmailNotificationObserver;
import com.example.eventmanagement .repository.PaymentRepository;
import com.example.eventmanagement .service.PaymentGenerationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PaymentGenerationServiceImpl – core business logic for Payment Report Generation.
 *
 * ✅ Design Pattern  : Facade Pattern
 *
 * ✅ Design Principle: Dependency Inversion Principle (DIP)
 */

public class PaymentGenerationServiceImpl implements PaymentGenerationService{
    public EventDetails getEventDetails(Long Id){
        EventDetailsFacade d = new EventDetailsFacade();
        Event event = (Event)d.getDetails();
        return event;
    }

    public ClubDetails getClubDetails(Long Id){
        ClubDetailsFacade d = new ClubDetailsFacade();
        Club club = (Club)d.getDetails();
        return club;
    }

    public PaymentDetails(Long Id){
        PaymentDetails d = new PaymentDetails();
        PaymentD paymentd = (PaymentD)d.getDetails();
        return paymentd;
    }
}