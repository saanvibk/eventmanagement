package com.example.eventmanagement.facade;

import com.example.eventmanagement.model.Payment;
import com.example.eventmanagement.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Facade over the Payment subsystem – returns a compact {@link PaymentDetails}
 * object for the payment report.
 */
@Component
public class PaymentDetailsFacade implements DetailsFacade {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentDetailsFacade(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Details getDetails(Long paymentId) {
        Payment p = paymentRepository.findById(paymentId).orElse(null);
        if (p == null) return new PaymentDetails();
        return new PaymentDetails(
                p.getId(),
                p.getType(),
                p.getAmount(),
                p.getStatus(),
                p.getPaidAt()
        );
    }
}