package com.example.eventmanagement .service.impl;

//import com.example.eventmanagement .dto.EventSearchDTO;
import com.example.eventmanagement .model.Payment;
import com.example.eventmanagement .model.Payment.PaymentStatus;
import com.example.eventmanagement .model.EventRegistration;
//import com.example.eventmanagement .observer.EventObserver;
//import com.example.eventmanagement .observer.EventSubject;
//import com.example.eventmanagement .observer.impl.DashboardNotificationObserver;
//import com.example.eventmanagement .observer.impl.EmailNotificationObserver;
import com.example.eventmanagement .repository.PaymentRepository;
import com.example.eventmanagement .service.PaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PaymentServiceImpl – core business logic for Payment Management.
 *
 * ✅ Design Pattern  : Facade Pattern
 *
 * ✅ Design Principle: Dependency Inversion Principle (DIP)
 */

public class PaymentServiceImpl implements PaymentService{
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment){
        return paymentRepository.save(payment);
    }

    public List<Payment> fetchPaymentList(Long memberId){
        return (List<Payment>) paymentRepository.findById(memberId).get();
    }

    public deletePaymentByMemberAndEventId(Long memberId, Long eventId){
        return STRING "EVENT REGISTRATION CANCELLED";
    }

    public generateFineNotification(Long memberId, Long paymentId){
        return STRING "FINE";
    }
}