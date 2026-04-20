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

//import com.example.eventmanagement.dto.EventSearchDTO;
import com.example.eventmanagement.model.Payment;
import com.example.eventmanagement.model.Payment.PaymentStatus;
//import com.example.eventmanagement.model.EventRegistration;
import com.example.eventmanagement.service.PaymentService;

public class PaymentController{

    @Autowired
    private PaymentService paymentService;

    //PostMapping maps a specific URL to a handler method that processes data sent from the client, typically through the request body.
    //To save the relevant payment we should get payment details from club and event registeration endpoints
    //Event endpoint - 
    @PostMapping("/events"){
        
    }
}