//FACADE PATTERN - DELEGATE RESPONSIBILITIES TO SUBSYSTEMS

package com.example.eventmanagement .service;
package structural.facade;

//import com.example.eventmanagement .dto.EventSearchDTO;
import com.example.eventmanagement .model.Payment;
import com.example.eventmanagement .model.Event.PaymentStatus;

import java.util.List;
import java.util.Optional;

//2) List the payments a member has made (member dashboard) - implement facade pattern to generate report

public class PaymentGenerationService{

    //Get event details to add to the report
    public EventDetails getEventDetails(Long Id);

    //Get club details to add to the report
    public ClubDetails getClubDetails(Long Id);

    //Get payment details to add to the report
    public PaymentDetails getPaymentDetails(Long Id);
}