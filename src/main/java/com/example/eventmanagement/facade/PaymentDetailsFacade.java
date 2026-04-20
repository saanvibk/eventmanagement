//Get payment details

package structural.facade;

public class PaymentDetailsFacade implements PaymentFacade {

    public Details getDetails()
    {
        PaymentDetails pd = new PaymentDetails();
        return pd;
    }
}