package com.example.eventmanagement .service;

//import com.example.eventmanagement .dto.EventSearchDTO;
import com.example.eventmanagement .model.Payment;
import com.example.eventmanagement .model.Event.PaymentStatus;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    //Define payment management operations

    //1) Save Payment in database (payments)
    Payment savePayment(Payment payment);

    //3) Remove Payment from database if member unregisters from a club / event
    @Modifying
    @Query("delete from payments p where p.memberId = ?1 AND p.eventId = ?2")
    void deletePaymentByMemberAndEventId(Long memberId, Long eventId); //To delete payment of a member for an event -> Should happen if a member cancels

    @Modifying
    @Query("delete from payments p where p.memberId = ?1 AND p.clubId = ?2")
    void deletePaymentByMemberAndClubId(Long memberId, Long clubId);  //To delete payment of a member if a member if member leaves club -> member is removed from members table

    //minor functionality -> fine_notifications (use table fine_notifications)
    void generateFineNotification(Long memberId, Long paymentId); //fetch paymentId records for that member from payments table
                                                                  //see paid_at time -> if longer than required time -> generate fine notification
                                                                  //update fine_notifications table
}